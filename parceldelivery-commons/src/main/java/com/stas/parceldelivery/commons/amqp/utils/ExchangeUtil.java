package com.stas.parceldelivery.commons.amqp.utils;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import java.util.stream.Stream;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;

public class ExchangeUtil {

	public static TopicExchange withTopicExchange(AmqpAdmin admin, String exchangeName) {
		TopicExchange exchange = new TopicExchange(exchangeName);
		admin.declareExchange(exchange);
		return exchange;
	}
	
	public static DirectExchange withDitrectExchange(AmqpAdmin admin, String exchangeName) {
		DirectExchange exchange = new DirectExchange(exchangeName);
		admin.declareExchange(exchange);
		return exchange;
	}
	
	public static void exchangeWithTopicToQueues(
			AmqpAdmin admin, boolean durable, 
			String exchangeName, 
			String routingKey, 
			String ... queues 
			) {
		
		ExchangeUtil.withTopicExchange(admin, exchangeName);
		Stream.of(queues).forEach(qn -> {
			Queue q = QueueUtil.withQueue(admin, qn, durable);
			admin.declareBinding(BindingBuilder
					   .bind(q)
					   .to(ExchangeUtil.withTopicExchange(admin, exchangeName))
					   .with(routingKey));
		});
		
	}
	
	
}
