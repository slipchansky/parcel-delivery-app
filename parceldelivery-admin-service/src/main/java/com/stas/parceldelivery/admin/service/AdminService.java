package com.stas.parceldelivery.admin.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderModification;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {

	@Autowired
	TaskService tasks;

	@Autowired
	TaskService couriers;

	@Transactional
	public void createOrder(OrderCreated order) {
		try {
			tasks.createTask(order);
		} catch (NotFoundException e) {
			log.debug("Task not found {}", order.getId());
		}
	}

	@Transactional
	public void changeStatus(OrderStatusChanged delta) {
		try {
			tasks.update(delta);
		} catch (NotFoundException e) {
			log.debug("Task not found {}", delta.getId());
		}
	}

	@Transactional
	public void updateOrder(OrderModification delta) {
		try {
			tasks.update(delta);
		} catch (NotFoundException e) {
			log.debug("Task not found {}", delta.getId());
		}
	}

	@Transactional
	public void cancelOrder(OrderCancelled cancel) {
		try {
			tasks.cancelOrder(cancel);
		} catch (NotFoundException e) {
			log.debug("Task not found {}", cancel.getId());
		}

	}

	@Transactional
	public void updateLocation(LocationChanged delta) {
		try {
			tasks.update(delta);
		} catch (NotFoundException e) {
			log.debug("Task not found {}", delta.getId());
		}

	}

}
