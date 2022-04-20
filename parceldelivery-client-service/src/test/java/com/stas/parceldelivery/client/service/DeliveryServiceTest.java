package com.stas.parceldelivery.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.client.repository.DeliveryRepository;
import com.stas.parceldelivery.commons.amqp.messages.DeliveryStatusChanged;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryOrderDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DeliveryService.class)
public class DeliveryServiceTest {
	
	@MockBean
	DeliveryRepository deliveryRepository;
	
	@MockBean
	ClientMessageTransmitter messageTransmitter;
	
	@Autowired
	DeliveryService deliveryService;

	@Test
	public void testCreate() {
		DeliveryOrderDTO d = DeliveryOrderDTO.builder().client("x").build();
		DeliveryOrder x = DeliveryOrder.fromDto(d); 
		when(deliveryRepository.save(x)).thenReturn(x);
		DeliveryOrderDTO result = deliveryService.create("1", d);
		assertEquals("1", result.getClient());
		verify(messageTransmitter, Mockito.times(1)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testCreateIdShuldBeNull() {
		final DeliveryOrderDTO d = DeliveryOrderDTO.builder().client("x").id("").build();
		DeliveryOrder x = DeliveryOrder.fromDto(d); 
		when(deliveryRepository.save(x)).thenReturn(x);
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, ()->deliveryService.create("1", d));
		assertEquals("Your request shouldn't contain id", thrown.getMessage());
		verify(deliveryRepository, Mockito.times(0)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateSuccess() {
		UpdateDestinationRequest u = new UpdateDestinationRequest ("x");
		DeliveryOrder d = DeliveryOrder.builder().client("x").id("1").build();
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(d));
		when(deliveryRepository.save(any(DeliveryOrder.class))).thenReturn(d);
		deliveryService.update("x", "1", u);
		verify(messageTransmitter, Mockito.times(1)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateErrorNotFound() {
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.empty());
		UpdateDestinationRequest u = new UpdateDestinationRequest ("x");
		DeliveryOrder d = DeliveryOrder.builder().client("x").id("1").build();
		NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, ()->deliveryService.update("x", "1", u));
		assertEquals("There is no such delivery", thrown.getMessage());
		verify(messageTransmitter, Mockito.times(0)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(0)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateErrorBadRequestException() {
		DeliveryOrder d = DeliveryOrder.builder().client("x").id("1").build();
		UpdateDestinationRequest u = new UpdateDestinationRequest ("x");
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(d));
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, ()->deliveryService.update("y", "1", u));
		assertEquals("There is no such delivery", thrown.getMessage());
		verify(messageTransmitter, Mockito.times(0)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(0)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testFindAll() {
		// is tested on dao level
	}
	
	@Test
	public void testFindDeliveriesUpToStatus() {
		// is tested on dao level
	}
	
	public void testFindDeliveriesByStatus() {
		// is tested on dao level
	}
	
	public void testUpdateDeliverySuccess() {
		DeliveryOrder d = DeliveryOrder.builder().client("x").id("1").build();
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(d));
		DeliveryStatusChanged c = DeliveryStatusChanged.builder().deliveryId("1").build();
		when(deliveryRepository.findById("1")).thenReturn(Optional.of(d));
		deliveryService.updateDeliveryStatus(c);
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
}
