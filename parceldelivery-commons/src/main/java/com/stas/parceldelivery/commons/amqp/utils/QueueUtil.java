package com.stas.parceldelivery.commons.amqp.utils;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;

public class QueueUtil {
	
	public static Queue withQueue(AmqpAdmin admin, String queueName, boolean durable) {
		Queue queue = new Queue(queueName, durable);
		admin.declareQueue(queue);
		return queue;
	}
	
	
	

}
