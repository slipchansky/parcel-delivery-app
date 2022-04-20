package com.stas.parceldelivery.client.amql;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.commons.constants.QueueNames;

@Component
public class CourierTestListener {
	
	@Autowired
	DummyService service;
	
	@RabbitListener(queues = QueueNames.COURIER_QUEUE)
    public void onDeliveryCreatedCourier(DeliveryOrder payload) throws IOException {
		service.doit();
    }

}
