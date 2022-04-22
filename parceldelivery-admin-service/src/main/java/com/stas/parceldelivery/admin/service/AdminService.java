package com.stas.parceldelivery.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.stas.parceldelivery.admin.amqp.AdminMessageTransmitter;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import lombok.extern.slf4j.Slf4j;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Service
@Slf4j
public class AdminService {
	
	@Autowired
	TaskRepository deliveryRepository;
	
	@Autowired
	AdminMessageTransmitter messageTransmitter;

	public void changeStatus(OrderStatusChanged payload) {
		// TODO Auto-generated method stub
		
	}


	public void createOrder(OrderCreated payload) {
		// TODO Auto-generated method stub
		
	}


	public void updateOrder(OrderUpdated payload) {
		// TODO Auto-generated method stub
		
	}


	public void cancelOrder(OrderCancelled payload) {
		// TODO Auto-generated method stub
		
	}


	public void updateLocation(LocationChanged payload) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
