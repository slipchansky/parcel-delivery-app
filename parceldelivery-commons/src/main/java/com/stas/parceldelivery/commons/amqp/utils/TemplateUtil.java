package com.stas.parceldelivery.commons.amqp.utils;

import java.util.Map;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;

public class TemplateUtil {
	
	private final static MessagePostProcessor HEADERS_POST_PROCESSOR = (m -> {
		CallContext context = CallContext.getInstance();
		Map<String, Object> headers = m.getMessageProperties().getHeaders();
		headers.put(ParceldeliveryHeaders.CORRELATION_ID, context.getCorrelationId());
		headers.put(ParceldeliveryHeaders.REQUEST_ID, context.getRequestId());
		return m;
	});
	
	public static RabbitTemplate createTemplate(ConnectionFactory connectionFactory, String exchangeName, String routingKey) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setRoutingKey(routingKey);
		template.setExchange(exchangeName);
		template.setReplyTimeout(2000);
		template.setBeforePublishPostProcessors(HEADERS_POST_PROCESSOR);
		return template;
	}	

}
