package com.stas.parceldelivery.commons.amqp.utils;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class TemplateUtil {
	
	public static RabbitTemplate createTemplate(ConnectionFactory connectionFactory, String exchangeName, String routingKey) {
		
		
		
		RabbitTemplate orderMessageTemplate = new RabbitTemplate(connectionFactory);
		orderMessageTemplate.setRoutingKey(routingKey);
		orderMessageTemplate.setExchange(exchangeName);
		orderMessageTemplate.setReplyTimeout(2000);
		return orderMessageTemplate;
	}	

}
