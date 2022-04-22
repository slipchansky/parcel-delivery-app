package com.stas.parceldelivery.admin.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Routes;


@Component
public class AdminMessageTransmitter {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqp;
	
	private RabbitTemplate courierAssignProducer;
	
	
	@PostConstruct
	public void init() {
		
		QueueUtil.withQueues(amqp, true, ClientOrderAssigned,CourierOrderAssigned);
		
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.ADMIN_EXCHANGE,
				Routes.OrderAssignment,
				ClientOrderAssigned, CourierOrderAssigned
				);
		
		
		courierAssignProducer = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE, Routes.OrderAssignment);
	}
	
	public void orderAssigned(OrderAssignment assignment) {
		courierAssignProducer.convertAndSend(assignment);
	}
	

}
