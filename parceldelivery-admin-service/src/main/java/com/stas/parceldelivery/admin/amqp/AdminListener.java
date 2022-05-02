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
import com.stas.parceldelivery.admin.service.AdminService;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminListener {

	@Autowired
	AdminService service;

	@Autowired
	AmqpAdmin admin;

	@PostConstruct
	public void init() {
		QueueUtil.withQueues(admin, true, AdminOrderCreated, AdminOrderCancelled, AdminOrderUpdated,
				AdminLocationChanged, AdminStatusChanged);

	}

	@RabbitListener(queues = AdminOrderCreated)
	public void adminOnCreated(OrderCreated payload, @Headers Map<String, Object> headers) throws IOException {
		try {
		service.createOrder(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Error creating order {}", payload);
		}
	}

	@RabbitListener(queues = AdminOrderUpdated)
	public void adminOnUpdated(OrderUpdated payload, @Headers Map<String, Object> headers) throws IOException {
		try {
			service.updateOrder(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Error updating order {}", payload.getId());
		}
	}

	@RabbitListener(queues = AdminOrderCancelled)
	public void onadminOnCancelles(OrderCancelled payload, @Headers Map<String, Object> headers) throws IOException {
		try {
			service.cancelOrder(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Error updating order {}", payload.getId());
		}
	}

	@RabbitListener(queues = AdminStatusChanged)
	public void onAdminStatusChanged(OrderStatusChanged payload, @Headers Map<String, Object> headers) throws IOException {
		try {
			service.changeStatus(payload);
		} catch (UnexpectedRollbackException  e) {
			log.debug("Error updating order {}", payload.getId());
		}
	}

	@RabbitListener(queues = AdminLocationChanged)
	public void onAdminLocationChanged(LocationChanged payload, @Headers Map<String, Object> headers) throws IOException {
		try {
			service.updateLocation(payload);
		} catch (UnexpectedRollbackException e) {
			log.debug("Error updating order {}", payload.getId());
		}

	}

}
