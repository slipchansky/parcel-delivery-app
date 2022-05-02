package com.stas.parceldelivery.client.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class AmqpConfiguration {
	
	@Bean
	public AmqpAdmin admin(ConnectionFactory connectionFactory) {
		org.springframework.amqp.core.Message m;
		
		
		return new RabbitAdmin(connectionFactory);
	}
		

}
