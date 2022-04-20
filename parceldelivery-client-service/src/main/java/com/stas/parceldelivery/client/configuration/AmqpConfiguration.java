package com.stas.parceldelivery.client.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stas.parceldelivery.commons.constants.QueueNames;


@Configuration
@EnableRabbit
public class AmqpConfiguration {
	
	@Bean
	public AmqpAdmin admin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
		
	@Bean("ClientTransmitter")
	public RabbitTemplate deliveryMessageTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate orderMessageTemplate = new RabbitTemplate(connectionFactory);
		orderMessageTemplate.setRoutingKey(QueueNames.ADMIN_QUEUE);
		orderMessageTemplate.setExchange(QueueNames.CLIENT_EXCHANGE);
		orderMessageTemplate.setReplyTimeout(2000);
		return orderMessageTemplate;
	}
	


}
