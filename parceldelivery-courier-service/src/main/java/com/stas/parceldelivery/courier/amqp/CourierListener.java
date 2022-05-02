package com.stas.parceldelivery.courier.amqp;

import static com.stas.parceldelivery.commons.constants.Queues.*;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

import com.rabbitmq.client.Channel;
import com.stas.parceldelivery.commons.amqp.messages.CourierAssignedTask;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.courier.service.CourierService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourierListener {

	@Autowired
	CourierService deliveryService;

	@Autowired
	AmqpAdmin admin;

	@PostConstruct
	public void init() {
		QueueUtil.withQueues(admin, true, CourierTaskAssigned, CourierOrderUpdated, CourierOrderCancelled);

	}

	@RabbitListener(queues = CourierTaskAssigned)
	public void onOrderAssigned(CourierAssignedTask payload, @Headers Map<String, Object> headers) throws IOException {

		deliveryService.createCourierTask(payload);
	}

	@RabbitListener(queues = CourierOrderUpdated)
	public void onOrderUpdated(OrderUpdated payload, @Headers Map<String, Object> headers)
			throws IOException {
		try {
			deliveryService.updateCourierTask(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Task not found {}", payload.getId());
		}
	}

	@RabbitListener(queues = CourierOrderCancelled)
	public void onOrderCancelled(OrderCancelled payload, @Headers Map<String, Object> headers)
			throws IOException {
		try {
			deliveryService.cancelCourierTask(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Task not found {}", payload.getId());
		}
	}

}
