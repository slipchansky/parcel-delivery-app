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
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@SpringBootTest(classes = DeliveryService.class)
public class DeliveryServiceTest {
	
	@MockBean
	DeliveryRepository deliveryRepository;
	
	@MockBean
	ClientMessageTransmitter messageTransmitter;
	
	@Autowired
	DeliveryService deliveryService;
	
	final String clientId = "x";
	final String source = "a";
	final String destination = "b";
	
	final DeliveryOrderRequestDTO orderRequest = DeliveryOrderRequestDTO.builder()
			.addressFrom(source)
			.addressTo(destination)
			.build();
	final DeliveryOrder orderEntity = from(orderRequest)
			.convert(DeliveryOrder.class)
			.update(o -> o.setClient(clientId))
			.get();
	
	UpdateDestinationRequest updateDestinationRequest = new UpdateDestinationRequest ("c");
	

	@Test
	public void testCreate() {
		when(deliveryRepository.save(any(DeliveryOrder.class))).thenReturn(orderEntity);
		DeliveryOrderResponseDTO result = deliveryService.create(clientId, orderRequest);
		assertEquals(source, result.getAddressFrom());
		assertEquals(destination, result.getAddressTo());
		verify(messageTransmitter, Mockito.times(1)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateSuccess() {
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(orderEntity));
		when(deliveryRepository.save(any(DeliveryOrder.class))).thenReturn(orderEntity);
		deliveryService.update(clientId, "x", updateDestinationRequest);
		verify(messageTransmitter, Mockito.times(1)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateErrorNotFound() {
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.empty());
		NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, ()->deliveryService.update(clientId, "1", updateDestinationRequest));
		assertEquals("There is no such delivery", thrown.getMessage());
		verify(messageTransmitter, Mockito.times(0)).deliveryUpdated(Mockito.any(DeliveryOrder.class));
		verify(deliveryRepository, Mockito.times(0)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testUpdateErrorBadRequestException() {
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(orderEntity));
		
		BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, ()->
		deliveryService.update("x", "1", new UpdateDestinationRequest ()));
		assertEquals("You should send new address in body", thrown.getMessage());
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
		when(deliveryRepository.findById(anyString())).thenReturn(Optional.of(orderEntity));
		DeliveryStatusChanged c = DeliveryStatusChanged.builder().deliveryId("1").build();
		when(deliveryRepository.findById("1")).thenReturn(Optional.of(orderEntity));
		deliveryService.updateDeliveryStatus(c);
		verify(deliveryRepository, Mockito.times(1)).save(any(DeliveryOrder.class));
	}
	
	@Test
	public void testFindOne() {
		when(deliveryRepository.findByIdAndClient(anyString(), anyString())).thenReturn(Optional.of(orderEntity));
		DeliveryOrderResponseDTO found = deliveryService.findOne(clientId, "1");
		assertEquals(orderEntity.getAddressFrom(), found.getAddressFrom());
		assertEquals(orderEntity.getAddressTo(), found.getAddressTo());
		
	}
	
	@Test
	public void testFindOneFailsNoSuchDelivery() {
		when(deliveryRepository.findByIdAndClient(anyString(), anyString())).thenReturn(Optional.empty());
		NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, ()->deliveryService.findOne(clientId, "1"));
		assertEquals("There is no such delivery order", thrown.getMessage());
	}
	
}
