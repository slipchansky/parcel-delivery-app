package com.stas.parceldelivery.commons.amqp.utils;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.AmqpAdmin;

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
	
}
