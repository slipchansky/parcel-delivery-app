package com.stas.parceldelivery.admin.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.admin.amqp.AdminMessageTransmitter;
import com.stas.parceldelivery.admin.domain.Courier;
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

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CourierService {
	
	public Courier registerCourier(String userId) {
		return null;
		
	}

}
