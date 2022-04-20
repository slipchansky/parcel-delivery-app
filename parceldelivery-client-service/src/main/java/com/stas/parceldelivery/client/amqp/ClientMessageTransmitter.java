package com.stas.parceldelivery.client.amqp;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.commons.amqp.utils.BindingUtil;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.QueueNames;

@Component
public class ClientMessageTransmitter {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqpAdmin;
	
	private RabbitTemplate fromClientNotificationsTemplate;
	
	@PostConstruct
	public void init() {
		
		// send to
		Queue toAdmin = QueueUtil.withQueue(amqpAdmin, QueueNames.ADMIN_QUEUE, true);

		// send to
		Queue toCourier = QueueUtil.withQueue(amqpAdmin, QueueNames.COURIER_QUEUE, true);
		
		
		// send to exchange
		TopicExchange destinationExchange = ExchangeUtil.withTopicExchange(amqpAdmin, QueueNames.CLIENT_EXCHANGE);
		
		BindingUtil.addBinding(amqpAdmin, destinationExchange, toAdmin, QueueNames.FROM_CLIENT);
		BindingUtil.addBinding(amqpAdmin, destinationExchange, toCourier, QueueNames.FROM_CLIENT);
		
		fromClientNotificationsTemplate =  TemplateUtil.createTemplate(connectionFactory,
				QueueNames.CLIENT_EXCHANGE,
				QueueNames.FROM_CLIENT);
	}
	
	public void deliveryUpdated(Delivery delivery) {
		fromClientNotificationsTemplate.convertAndSend(delivery);
	}

}
