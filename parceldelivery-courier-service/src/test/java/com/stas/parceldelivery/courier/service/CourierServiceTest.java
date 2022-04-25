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
	
//	@MockBean
//	TaskRepository deliveryRepository;
//	
//	@MockBean
//	CourierTransmitter messageTransmitter;
//	
//	@Autowired
//	CourierService deliveryService;
//	
//	final String clientId = "x";
//	final String source = "a";
//	final String destination = "b";
//	
//	final DeliveryOrderRequestDTO orderRequest = DeliveryOrderRequestDTO.builder()
//			.addressFrom(source)
//			.addressTo(destination)
//			.build();
//	final CourierDeliveryTask orderEntity = from(orderRequest)
//			.convert(CourierDeliveryTask.class)
//			.update(o -> o.setClient(clientId))
//			.get();
//	
//	UpdateDestinationRequest updateDestinationRequest = new UpdateDestinationRequest ("c");
//	
//	private void mockSave() {
//		when(deliveryRepository.save(any(CourierDeliveryTask.class))).thenReturn(orderEntity);
//	}
//	
//	private void mockFindByIdThenOptionalOf() {
//		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(orderEntity));
//	}
//	
//	private void mockFindByIdThenOptionalEmpty() {
//		when(deliveryRepository.findById(anyString())).thenReturn(Optional.empty());
//	}
//	
//	private TaskRepository verifyRepositoryCall() {
//		return verify(deliveryRepository, times(1));
//	}
//
//	private CourierTransmitter verifyTransmitterCall() {
//		return verify(messageTransmitter, times(1));
//	}
//	
//	
//	
//
//	@Test
//	public void testCreate() {
//		mockSave();
//		DeliveryOrderResponseDTO result = deliveryService.create(clientId, orderRequest);
//		assertEquals(source, result.getAddressFrom());
//		assertEquals(destination, result.getAddressTo());
//		verifyTransmitterCall().orderCreated(Mockito.any(OrderCreated.class));
//		verifyRepositoryCall().save(any(CourierDeliveryTask.class));
//	}
//	
//
//	
//	@Test
//	public void testUpdateSuccess() {
//		mockFindByIdThenOptionalOf();
//		mockSave();
//		deliveryService.update(clientId, "x", updateDestinationRequest);
//		verifyTransmitterCall().orderUpdated(Mockito.any(OrderUpdated.class));
//		verifyRepositoryCall().save(any(CourierDeliveryTask.class));
//	}
//
//
//	
//	@Test
//	public void testUpdateErrorNotFound() {
//		mockFindByIdThenOptionalEmpty();
//		NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, ()->deliveryService.update(clientId, "1", updateDestinationRequest));
//		assertEquals("There is no such delivery", thrown.getMessage());
//		verify(messageTransmitter, Mockito.times(0)).orderUpdated(Mockito.any(OrderUpdated.class));
//		verify(deliveryRepository, Mockito.times(0)).save(any(CourierDeliveryTask.class));
//	}
//
//	
//	@Test
//	public void testUpdateErrorBadRequestException() {
//		mockFindByIdThenOptionalOf();
//		
//		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, ()->
//			deliveryService.update("x", "1", new UpdateDestinationRequest ()));
//		
//		assertEquals("You should send new address in body", thrown.getMessage());
//		verify(messageTransmitter, Mockito.times(0)).orderCreated(Mockito.any(OrderCreated.class));
//		verify(deliveryRepository, Mockito.times(0)).save(any(CourierDeliveryTask.class));
//	}
//	
//	@Test
//	public void testFindAll() {
//		// is tested on dao level
//	}
//	
//	@Test
//	public void testFindDeliveriesUpToStatus() {
//		// is tested on dao level
//	}
//	
//	public void testFindDeliveriesByStatus() {
//		// is tested on dao level
//	}
//	
//	@Test
//	public void testUpdateDeliverySuccess() {
//		OrderStatusChanged c = OrderStatusChanged.builder().id("1").build();
//		when(deliveryRepository.findById("1")).thenReturn(Optional.of(orderEntity));
//		
//		deliveryService.updateStatus(c);
//		verifyRepositoryCall().save(any(CourierDeliveryTask.class));
//	}
//	
//	@Test
//	public void testFindOne() {
//		when(deliveryRepository.findByIdAndClient(anyString(), anyString())).thenReturn(Optional.of(orderEntity));
//		DeliveryOrderResponseDTO found = deliveryService.findOne(clientId, "1");
//		assertEquals(orderEntity.getAddressFrom(), found.getAddressFrom());
//		assertEquals(orderEntity.getAddressTo(), found.getAddressTo());
//		
//	}
//	
//	@Test
//	public void testFindOneFailsNoSuchDelivery() {
//		when(deliveryRepository.findByIdAndClient(anyString(), anyString())).thenReturn(Optional.empty());
//		NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, ()->deliveryService.findOne(clientId, "1"));
//		assertEquals("There is no such delivery order", thrown.getMessage());
//	}
	
}
