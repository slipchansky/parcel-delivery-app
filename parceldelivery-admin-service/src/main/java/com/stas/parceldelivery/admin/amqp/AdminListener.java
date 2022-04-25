package com.stas.parceldelivery.admin.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.AdminLocationChanged;
import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderCancelled;
import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderCreated;
import static com.stas.parceldelivery.commons.constants.Queues.AdminOrderUpdated;
import static com.stas.parceldelivery.commons.constants.Queues.AdminStatusChanged;
import static com.stas.parceldelivery.commons.constants.Queues.ClientLocationChanged;
import static com.stas.parceldelivery.commons.constants.Queues.ClientOrderAssigned;
import static com.stas.parceldelivery.commons.constants.Queues.ClientStatusChanged;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.admin.service.AdminService;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;

@Component
public class AdminListener {
	
	@Autowired
	AdminService service;
	
	@Autowired
	AmqpAdmin admin;
	

	@PostConstruct
	public void init() {
		QueueUtil.withQueues(admin, true, 
				AdminOrderCreated,
				AdminOrderCancelled, 
				AdminOrderUpdated, 
				AdminLocationChanged, 
				AdminStatusChanged);
		
	}
	
	
	@RabbitListener(queues = AdminOrderCreated)
    public void adminOnCreated(OrderCreated payload) throws IOException {
		service.createOrder(payload);
    }
	
	@RabbitListener(queues = AdminOrderUpdated)
    public void adminOnUpdated(OrderUpdated payload) throws IOException {
		service.updateOrder(payload);
    }
	
	
	@RabbitListener(queues = AdminOrderCancelled)
    public void onadminOnCancelles(OrderCancelled payload) throws IOException {
		service.cancelOrder(payload);
    }
	
	@RabbitListener(queues = AdminStatusChanged)
    public void onAdminStatusChanged(OrderStatusChanged payload) throws IOException {
		service.changeStatus(payload);
    }
	
	@RabbitListener(queues = AdminLocationChanged)
    public void onAdminLocationChanged(LocationChanged payload) throws IOException {
		service.updateLocation(payload);
    }
	
	
}
