package com.stas.parceldelivery.client.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.ClientLocationChanged;
import static com.stas.parceldelivery.commons.constants.Queues.ClientOrderAssigned;
import static com.stas.parceldelivery.commons.constants.Queues.ClientStatusChanhed;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.client.service.DeliveryService;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;

@Component
public class ClientListener {
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AmqpAdmin admin;
	

	@PostConstruct
	public void init() {
		QueueUtil.withQueues(admin, true, ClientStatusChanhed, ClientLocationChanged, ClientOrderAssigned);
		
	}
	
	
	@RabbitListener(queues = ClientStatusChanhed)
    public void onStatusChanged(
    		OrderStatusChanged payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.updateStatus(payload);
    }
	
	@RabbitListener(queues = ClientOrderAssigned)
    public void onOrderAssigned(
    		OrderAssignment payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.deliveryAssigned(payload);
    }
	
	@RabbitListener(queues = ClientLocationChanged)
    public void onLocationChanged(
    		LocationChanged payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.updateLocation(payload);
    }
	
	
}
