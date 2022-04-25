package com.stas.parceldelivery.courier.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.courier.amqp.CourierTransmitter;
import com.stas.parceldelivery.courier.domain.CourierTask;
import com.stas.parceldelivery.courier.repository.CourierTaskRepository;
import com.stas.parceldelivery.courier.service.CourierService;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@SpringBootTest(classes = CourierService.class)
public class CourierServiceTest {
	
	// FIXME stas. should be implemented
	
}
