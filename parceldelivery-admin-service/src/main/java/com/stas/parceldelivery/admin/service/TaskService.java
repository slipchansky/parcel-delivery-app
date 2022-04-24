package com.stas.parceldelivery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.admin.amqp.AdminMessageTransmitter;
import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.domain.DeliveryTaskTrace;
import com.stas.parceldelivery.admin.repository.CourierRepository;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.admin.repository.TaskTraceRepository;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderModification;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.enums.TaskState;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDetailsDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskTraceDTO;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository tasks;
	
	@Autowired
	private TaskTraceRepository traces;
	
	@Autowired
	private CourierRepository couriers;
	
	@Autowired
	AdminMessageTransmitter transmitter;
	
	
	private void addTrace(final DeliveryTask task) {
		DeliveryTaskTrace trace = from(task)
				.convert(DeliveryTaskTrace.class)
				.update(x -> {
					x.setId(UUID.randomUUID().toString());
					x.setTask(task);
				}).get();
		traces.save(trace);
	}
	
	
	@Transactional
	public DeliveryTask createTask(OrderCreated order) {
		DeliveryTask task = from(order)
		.convert(DeliveryTask.class)
		.update(x -> {
		       x.setStatus(DeliveryStatus.CREATED);
		       x.setLocation(order.getAddressFrom());
		       x.setState(TaskState.NEW);
		}).get();
		
		task = tasks.save(task);
		addTrace(task);
		return task;
	}

	
	@Transactional
	public DeliveryTask update(OrderModification delta) {
		Optional<DeliveryTask> found = tasks.findById(delta.getId());
		if(!found.isPresent()) {
			throw new NotFoundException("There is no order "+delta.getId());
		}
		DeliveryTask task = from(found.get()).with(delta);
		if(task.getStatus()==DeliveryStatus.FINISHED) {
			task.setAssignee(null);
		}
		task.setState(TaskState.fromDeliveryStatus(task.getStatus()));
		task = tasks.save(task);
		addTrace(task);
		return task;
	}
	
	
	@Transactional
	public DeliveryTaskDTO dismiss(OrderCancelled cancel) {
		Optional<DeliveryTask> found = tasks.findById(cancel.getId());
		if(!found.isPresent()) {
			throw new NotFoundException("There is no order "+cancel.getId());
		}
		DeliveryTask task = found.get();
		task.setStatus(DeliveryStatus.CANCELED);
		task.setAssignee(null);
		task.setState(TaskState.CANCELLED);
		task = tasks.save(task);
		addTrace(task);
		return from(task).to(DeliveryTaskDTO.class);
	}
	
	public List<DeliveryTaskDTO> getUnassigned(){
		return tasks.findAllByState(TaskState.NEW).stream()
				.map(t -> from(t).to(DeliveryTaskDTO.class))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public DeliveryTaskDTO assignTask(String taskId, String courierId) {
		Optional<Courier> oCourier = couriers.findById(courierId);
		if(!oCourier.isPresent()) {
			throw new BadRequestException("There is no courier with id="+courierId);
		}
		
		Optional<DeliveryTask> found = tasks.findById(taskId);
		if(!found.isPresent()) {
			throw new NotFoundException("There is no task with id= "+taskId);
		}
		DeliveryTask task = found.get();
		task.setStatus(DeliveryStatus.ASSIGNED);
		task.setAssignee(oCourier.get());
		task = tasks.save(task);
		addTrace(task);
		
		OrderAssignment.builder()
			.id(task.getId())
			.assignee(courierId);
		transmitter.orderAssigned(from(task).to(OrderAssignment.class));
		return from(task).to(DeliveryTaskDTO.class);
	}


	public DeliveryTask cancelOrder(OrderCancelled cancel) {
		Optional<DeliveryTask> found = tasks.findById(cancel.getId());
		if(!found.isPresent()) {
			throw new BadRequestException("There is no order with id="+cancel.getId());
		}
		tasks.delete(found.get());
		return found.get();
		
	}


	public List<DeliveryTaskDTO> getAllTasks() {
		return StreamSupport.stream(tasks.findAll().spliterator(), false)
		.map(t -> from(t).to(DeliveryTaskDTO.class))
		.collect(Collectors.toList());
		
	}


	public DeliveryTaskDetailsDTO getTaskDetails(String taskId) {
		Optional<DeliveryTask> found = tasks.findById(taskId);
		if (!found.isPresent()) {
			throw new NotFoundException("There is no task with id "+taskId);
		}
		DeliveryTaskDetailsDTO task = from(found.get()).to(DeliveryTaskDetailsDTO.class);
		List<DeliveryTaskTrace> tracking = traces.findAllByTaskId(taskId);
		task.setHistory(tracking.stream().map(t -> from(t).to(DeliveryTaskTraceDTO.class)).collect(Collectors.toList()));
		return task;
	}
	

}
