package com.stas.parceldelivery.commons.amqp.utils;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.AmqpAdmin;

import com.stas.parceldelivery.commons.amqp.utils.exceptions.CantDeclareBindingException;


public class BindingUtil {
	
	public static Binding addBinding(AmqpAdmin admin, AbstractExchange exchange, Queue queue, String rouditnKey) {
		Binding binding = null;
		switch(exchange.getClass().getSimpleName()) {
		case "TopicExchange":
			binding = BindingBuilder
			   .bind(queue)
			   .to((TopicExchange)exchange)
			   .with(rouditnKey);
			break;
		case "DirectExchange":
			binding = BindingBuilder
			   .bind(queue)
			   .to((DirectExchange)exchange)
			   .with(rouditnKey);
			break;
		case "FanoutExchange":
			binding = BindingBuilder
			   .bind(queue)
			   .to((FanoutExchange)exchange);
			break;
		}
		if(binding==null) {
			// TODO stas. other oerrors during binding declaration also should be considered
			throw new CantDeclareBindingException();
		}
		admin.declareBinding(binding);
		
		return binding;
	}

}
