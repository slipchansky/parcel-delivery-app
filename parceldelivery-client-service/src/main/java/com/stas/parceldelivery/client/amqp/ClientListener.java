package com.stas.parceldelivery.client.amqp;

import java.io.IOException;


import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.client.service.DeliveryService;
import com.stas.parceldelivery.commons.amqp.messages.DeliveryStatusChanged;
import com.stas.parceldelivery.commons.amqp.utils.BindingUtil;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.QueueNames;

@Component
public class ClientListener {
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AmqpAdmin amqpAdmin;
	

	@PostConstruct
	public void init() {
		
		// listen to
		QueueUtil.withQueue(amqpAdmin, QueueNames.CLIENT_QUEUE, true);
		
	}
	
	
	@RabbitListener(queues = QueueNames.CLIENT_QUEUE)
    public void onDeeliveryChamged(
    		DeliveryStatusChanged payload, 
    		Channel channel, 
    		@Header(AmqpHeaders.DELIVERY_TAG) long tag
    		) throws IOException {
        deliveryService.updateDeliveryStatus(payload);
    }
	
	
	
}
