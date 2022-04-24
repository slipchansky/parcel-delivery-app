package com.stas.parceldelivery.admin.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.admin.amqp.AdminMessageTransmitter;
import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.CourierStatus;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.domain.DeliveryTaskTrace;
import com.stas.parceldelivery.admin.domain.TaskState;
import com.stas.parceldelivery.admin.repository.CourierRepository;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.admin.repository.TaskTraceRepository;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderModification;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CourierService {
	
	
	@Autowired
	UserDetailsServiceClient userService;
	
	@Autowired
	UserServiceClient userDetailsService;
	
	@Autowired 
	CourierRepository repository;
	
	public Courier registerCourier(String userId) {
		Courier courier = retrieveCourierFromUser(userId);
		courier.setStatus(CourierStatus.FREE);
		return repository.save(courier);
		
	}

	Courier retrieveCourierFromUser(String userId) {
		UserDTO user = userDetailsService.get(userId);
		UserDetailsDTO details = userService.getDetails(userId);
		Courier courier = userToCourier(user, details);
		return courier;
	}

	static Courier userToCourier(UserDTO user, UserDetailsDTO details) {
		Courier courier = from(details).to(Courier.class);
		courier = from(courier).with(user);
		courier.setId(user.getUsername());
		return courier;
	}
	
	public List<Courier> getCouriers(CourierStatus status){
		return repository.findAllByStatus(status);
	}
	
	

}
