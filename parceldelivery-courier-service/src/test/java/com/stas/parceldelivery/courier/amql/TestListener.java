package com.stas.parceldelivery.courier.amql;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;

@Component
public class TestListener {
	
	@Autowired
	DummyService service;
	
	@Autowired
	AmqpAdmin amqp;
	
	@PostConstruct
	public void init() {
		QueueUtil.withQueues(amqp, true, AdminStatusChanged, ClientStatusChanged, AdminLocationChanged, ClientLocationChanged);
	}
	
	@RabbitListener(queues = AdminStatusChanged)
    public void onAdminStatusChanged(OrderStatusChanged payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = ClientStatusChanged)
    public void onClientStatusChanged(OrderStatusChanged payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = AdminLocationChanged)
    public void onAdminLocationChanged(LocationChanged payload) throws IOException {
		service.doit();
    }
	
	@RabbitListener(queues = ClientLocationChanged)
    public void onClientLocationChanged(LocationChanged payload) throws IOException {
		service.doit();
    }
}
