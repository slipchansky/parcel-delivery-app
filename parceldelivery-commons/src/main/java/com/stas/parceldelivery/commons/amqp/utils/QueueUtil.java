package com.stas.parceldelivery.commons.amqp.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

import com.stas.parceldelivery.commons.constants.ExchangeName;

public class QueueUtil {
	
	public static Queue withQueue(AmqpAdmin admin, String queueName, boolean durable) {
		Queue queue = new Queue(queueName, durable);
		admin.declareQueue(queue);
		return queue;
	}
	
	public static void withQueues(AmqpAdmin admin, boolean durable, String ... queueName) {
		Stream.of(queueName).forEach( n -> withQueue(admin, n, durable));
	}

	public static void purgeQueues(AmqpAdmin admin, List<String> all) {
		all.forEach( q -> {
			try {
			admin.purgeQueue(q);
			} catch(Throwable e) {}
		});
	}
	
	public static void purgeQueues(AmqpAdmin admin, String ...q) {
		purgeQueues(admin, Arrays.asList(q));
	}
	
	
	
	

}
