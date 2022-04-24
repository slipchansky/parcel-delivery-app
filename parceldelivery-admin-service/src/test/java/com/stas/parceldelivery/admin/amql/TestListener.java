package com.stas.parceldelivery.admin.amql;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;


@Component
public class TestListener {
	
	@Autowired
	DummyService service;
	
	@Autowired
	AmqpAdmin amqp;
	
	@PostConstruct
	public void init() {
		QueueUtil.withQueues(amqp, true, ClientOrderAssigned, CourierOrderAssigned);

	}
	
	@RabbitListener(queues = ClientOrderAssigned)
    public void clientClientOrderAssigned(OrderAssignment payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = CourierOrderAssigned)
    public void adminOnUpdated(OrderAssignment payload) throws IOException {
		service.doit();
    }
	
	
	

}
