package com.stas.parceldelivery.client.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderCancelled;
import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderCreated;
import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderUpdated;
import static com.stas.parceldelivery.commons.constants.Queues.CourierOrderCancelled;
import static com.stas.parceldelivery.commons.constants.Queues.CourierOrderUpdated;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Routes;


@Component
public class ClientMessageTransmitter {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqp;
	
	private RabbitTemplate orderCreated;
	private RabbitTemplate orderUpdated;
	private RabbitTemplate orderCancelled;
	
	
	@PostConstruct
	public void init() {
		
		QueueUtil.withQueues(amqp, true, AdminOrderCreated, AdminOrderUpdated, CourierOrderUpdated, AdminOrderCancelled, CourierOrderCancelled);
		
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderCreated,
				AdminOrderCreated
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderUpdated,
				AdminOrderUpdated, CourierOrderUpdated
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderCancelled,
				AdminOrderCancelled,  CourierOrderCancelled);
		
		orderCreated = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderCreated);
		orderUpdated = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderUpdated);
		orderCancelled = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderCancelled);
	}
	
	public void orderCreated(OrderCreated created) {
		orderCreated.convertAndSend(created);
	}
	
	public void orderUpdated(OrderUpdated update) {
		orderUpdated.convertAndSend(update);
	}
	
	public void orderCancelled(OrderCancelled cancel) {
		orderCancelled.convertAndSend(cancel);
	}

}
