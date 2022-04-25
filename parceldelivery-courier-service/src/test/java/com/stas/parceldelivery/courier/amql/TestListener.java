package com.stas.parceldelivery.courier.amql;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.courier.domain.CourierTask;

@Component
public class TestListener {
	
	@Autowired
	DummyService service;
	
	@Autowired
	AmqpAdmin amqp;
	
	@PostConstruct
	public void init() {
		QueueUtil.withQueues(amqp, true, AdminOrderCreated, AdminOrderUpdated, CourierOrderUpdated, 
				AdminOrderCancelled, CourierOrderCancelled);
	}
	
	@RabbitListener(queues = AdminOrderCreated)
    public void adminOnCreated(OrderCreated payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = AdminOrderUpdated)
    public void adminOnUpdated(OrderUpdated payload) throws IOException {
		service.doit();
    }
	
	
	@RabbitListener(queues = AdminOrderCancelled)
    public void onadminOnCancelles(OrderCancelled payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = AdminStatusChanged)
    public void onAdminStatusChanged(OrderStatusChanged payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = AdminLocationChanged)
    public void onAdminLocationChanged(LocationChanged payload) throws IOException {
		service.doit();
    }
	
	
	@RabbitListener(queues = CourierOrderCancelled)
    public void onCourierOrderCancelled(OrderCancelled payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = CourierOrderUpdated)
    public void courierOnUpdated(OrderUpdated payload) throws IOException {
		service.doit();
    }
	

	@RabbitListener(queues = CourierTaskAssigned)
    public void onCourierOrderAssigned(OrderAssignment payload) throws IOException {
		service.doit();
    }
	

}
