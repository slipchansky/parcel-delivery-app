package com.stas.parceldelivery.courier.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.stas.parceldelivery.commons.amqp.messages.CourierAssignedTask;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.CourierTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.courier.amqp.CourierTransmitter;
import com.stas.parceldelivery.courier.domain.CourierTask;
import com.stas.parceldelivery.courier.repository.CourierTaskRepository;

import lombok.extern.slf4j.Slf4j;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Service
@Slf4j
public class CourierService {
	
	@Autowired
	CourierTaskRepository  tasks;
	
	@Autowired
	CourierTransmitter transmitter;
	

	@Transactional
	public void updateCourierTask(OrderUpdated payload) {
		Optional<CourierTask> found = tasks.findById(payload.getId());
		if(!found.isPresent()) {
			log.debug("Task not found, taskId= {}", payload.getId());
			throw new NotFoundException("Task not found, taskId = "+payload.getId());
		}
		CourierTask task = from(found.get()).with(payload);
		tasks.save(task);
		log.debug("Task updated {}", task);
	}


	@Transactional
	public void cancelCourierTask(OrderCancelled payload) {
		Optional<CourierTask> found = tasks.findById(payload.getId());
		if(!found.isPresent()) {
			log.debug("Task not found, taskId= {}", payload.getId());
			throw new NotFoundException("Task not found, taskId = "+payload.getId());
		}
		tasks.delete(found.get());
		log.debug("Task updated {}", found.get());
	}


	@Transactional
	public void createCourierTask(CourierAssignedTask payload) {
		CourierTask task = from(payload).to(CourierTask.class);
		tasks.save(task);
		log.debug("Task created {}", task);
	}
	
	public List<CourierTaskDTO> findAllMyTasks(String courierId) {
		return tasks.findAllByCourierId(courierId).stream().map(this::toDto).collect(Collectors.toList());
	}

	@Transactional
	private CourierTask findTaskOrThrowError(String courierId, String id) {
		Optional<CourierTask> found = tasks.findById(id);
		CourierTask task = found.get();
		if (!found.isPresent() || !courierId.equals(task.getCourierId())) {
			throw new NotFoundException("There is no task {} assigned");
		}
		return task;
	}
	
	public CourierTaskDTO getTaskById(String courierId, String id) {
		return toDto(findTaskOrThrowError(courierId, id));
	}
	
	private CourierTaskDTO toDto(CourierTask t) {
		return from(t).to(CourierTaskDTO.class);
	} 

	
	@Transactional
	public CourierTaskDTO updateLocation(String courierId, String id, String location) {
		CourierTask task = findTaskOrThrowError(courierId, id);
		task.setLocation(location);
		tasks.save(task);
		transmitter.locationChanged(LocationChanged.builder().id(id).location(location).build());
		return toDto(task);
	}
	
	@Transactional
	public CourierTaskDTO  startDeliverying(String courierId, String id) {
		CourierTask task = findTaskOrThrowError(courierId, id);
		task.setStatus(DeliveryStatus.INPROGRESS);
		tasks.save(task);
		transmitter.statusChanged(OrderStatusChanged.builder().id(id).status(DeliveryStatus.INPROGRESS).build());
		return toDto(task);
	}
	
	@Transactional
	public CourierTaskDTO  finishDeliverying(String courierId, String id) {
		CourierTask task = findTaskOrThrowError(courierId, id);
		tasks.delete(task); // that is up to discussion
		transmitter.statusChanged(OrderStatusChanged.builder().id(id).status(DeliveryStatus.FINISHED).build());
		return toDto(task);
	}
	
	

}
