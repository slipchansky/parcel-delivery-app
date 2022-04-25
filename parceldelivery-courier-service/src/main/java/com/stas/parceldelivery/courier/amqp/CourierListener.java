package com.stas.parceldelivery.courier.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.commons.amqp.messages.CourierAssignedTask;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.courier.service.CourierService;

@Component
public class CourierListener {
	
	@Autowired
	CourierService deliveryService;
	
	@Autowired
	AmqpAdmin admin;
	

	@PostConstruct
	public void init() {
		QueueUtil.withQueues(admin, true,
				CourierTaskAssigned,CourierOrderUpdated,CourierOrderCancelled);
		
	}
	
	
	
	@RabbitListener(queues = ClientOrderAssigned)
    public void onOrderAssigned(
    		CourierAssignedTask payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.createCourierTask (payload);
    }
	
	
	@RabbitListener(queues = CourierOrderUpdated)
    public void onOrderUpdated(
    		OrderUpdated payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.updateCourierTask(payload);
    }
	
	
	@RabbitListener(queues = CourierOrderCancelled)
    public void onOrderCancelled(
    		OrderCancelled payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.cancelCourierTask(payload);
    }
	
	
	
}
