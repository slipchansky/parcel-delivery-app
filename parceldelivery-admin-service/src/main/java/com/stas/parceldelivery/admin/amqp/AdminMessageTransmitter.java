package com.stas.parceldelivery.admin.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.commons.amqp.messages.CourierAssignedTask;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Routes;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Component
public class AdminMessageTransmitter {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqp;
	
	private RabbitTemplate toClient;
	private RabbitTemplate toCourier;
	
	
	@PostConstruct
	public void init() {
		
		QueueUtil.withQueues(amqp, true, ClientOrderAssigned,CourierTaskAssigned);
		
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.ADMIN_EXCHANGE,
				Routes.OrderAssignment,
				ClientOrderAssigned
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.ADMIN_EXCHANGE,
				Routes.CourierTaskAssigned,
				CourierTaskAssigned
				);
		
		
		toClient = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE, Routes.OrderAssignment);
		toCourier = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE, Routes.CourierTaskAssigned);
	}
	
	public void orderAssigned(DeliveryTask task) {
		toClient.convertAndSend(OrderAssignment.builder()
				.id(task.getId())
				.assignee(task.getId())
				.build()
				);
		
		CourierAssignedTask courierTask = from(task).to(CourierAssignedTask.class);
		courierTask.setCourierId(task.getAssignee().getId());
		toCourier.convertAndSend(courierTask);
	}
	

}
