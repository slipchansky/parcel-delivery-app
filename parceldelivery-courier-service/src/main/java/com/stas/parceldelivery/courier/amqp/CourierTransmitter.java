package com.stas.parceldelivery.courier.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Routes;
import com.stas.parceldelivery.courier.domain.CourierTask;


@Component
public class CourierTransmitter {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqp;
	
	private RabbitTemplate statusChanged;
	private RabbitTemplate locationChanged;
	
	
	@PostConstruct
	public void init() {
		
		QueueUtil.withQueues(amqp, true, 
				AdminStatusChanged, ClientStatusChanhed, AdminLocationChanged, ClientLocationChanged
				);
		
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.COURIER_EXCHANGE,
				Routes.OrderStatusChanged,
				AdminStatusChanged, ClientStatusChanhed
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.COURIER_EXCHANGE,
				Routes.LocationChanged,
				AdminLocationChanged, ClientLocationChanged
				);
		
		
		statusChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE, Routes.OrderStatusChanged);
		locationChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE, Routes.LocationChanged);
	}
	
	public void statusChanged(OrderStatusChanged delta) {
		statusChanged.convertAndSend(delta);
	}
	
	public void locationChanged(LocationChanged delta) {
		locationChanged.convertAndSend(delta);
	}

}
