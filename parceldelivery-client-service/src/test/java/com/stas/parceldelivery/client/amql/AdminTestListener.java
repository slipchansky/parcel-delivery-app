package com.stas.parceldelivery.client.amql;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.commons.constants.QueueNames;

@Component
public class AdminTestListener {
	
	@Autowired
	DummyService service;
	
	
	@RabbitListener(queues = QueueNames.ADMIN_QUEUE)
    public void onUpdate(Delivery payload) throws IOException {
		service.doit();
    }
	

}
