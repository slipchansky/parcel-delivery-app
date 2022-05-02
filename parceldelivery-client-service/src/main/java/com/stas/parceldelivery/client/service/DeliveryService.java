package com.stas.parceldelivery.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.client.repository.DeliveryRepository;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import lombok.extern.slf4j.Slf4j;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Service
@Slf4j
public class DeliveryService {
	
	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Autowired
	ClientMessageTransmitter messageTransmitter;
	
	
	
	@Transactional
	public DeliveryOrderResponseDTO create(String clientId, @Validated DeliveryOrderRequestDTO d) {
		DeliveryOrder order = from(d).to(DeliveryOrder.class);
		order.setClient(clientId);
		order.setStatus(DeliveryStatus.CREATED);
		DeliveryOrder result = deliveryRepository.save(order);
		log.debug("New delivery created: {}", d);
		messageTransmitter.orderCreated(from(result).to(OrderCreated.class));
		log.debug("Delivery instance posted to queues: {}", d);
		return from(result).to(DeliveryOrderResponseDTO.class);
	}
	

	
	@Transactional
	public DeliveryOrderResponseDTO update(String clientId, String id, @Validated UpdateDestinationRequest d) {
		if(!StringUtils.hasText(d.getAddressTo())) {
			// TODOO stas. move to bean validation
			log.debug("You should send new address in request body");
			throw new BadRequestException("You should send new address in body");
			
		}
		
		Optional<DeliveryOrder> found = deliveryRepository.findById(id);
		if(!found.isPresent()) {
			log.debug("Delivery not found by id", d);
			throw new NotFoundException("There is no such delivery");
		}
		
		DeliveryOrder existing = found.get();
		
			
		if(!clientId.equals(existing.getClient())) {
			log.debug("Delivery client does not match", d);
			throw new BadRequestException("There is no such delivery");
		}
		
		if (d.getAddressTo().equals(existing.getAddressTo())) {
			// TODO. stas. cover by UT
			// nothing to do
			return from(existing).to(DeliveryOrderResponseDTO.class);
		}
		
		existing.setAddressTo(d.getAddressTo());
		DeliveryOrder result = deliveryRepository.save(existing);
		log.debug("Delivery updated: {}", d);
		messageTransmitter.orderUpdated( from(result).to(OrderUpdated.class) );
		log.debug("Delivery instance posted to queues: {}", d);
		return from(result).to(DeliveryOrderResponseDTO.class);
	}

	public List<DeliveryOrderResponseDTO> findAll(String clientId) {
		return deliveryRepository
				.findAllByClient(clientId).stream()
				.map(d -> from(d).to(DeliveryOrderResponseDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<DeliveryOrderResponseDTO> findDeliveriesUpToStatus(String userId, DeliveryStatus toStatus) {
		return deliveryRepository.findAllByClientAndStatusLessThan(userId, toStatus).stream()
				.map(d -> from(d).to(DeliveryOrderResponseDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<DeliveryOrderResponseDTO> findDeliveriesByStatus(String userId, DeliveryStatus status) {
		return deliveryRepository.findAllByClientAndStatus(userId, status).stream()
				.map(d -> from(d).to(DeliveryOrderResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public void updateStatus(OrderStatusChanged delta) {
		Optional<DeliveryOrder> found = deliveryRepository.findById(delta.getId());
		if(found.isPresent()) {
		   DeliveryOrder delivery = found.get();
		   delivery.setStatus(delta.getStatus());
		   // TODO. process possible errors
		   deliveryRepository.save(delivery);
		} else {
		  // TODO. implement what then
		}
	}
	
	@Transactional
	// FIXME. stas. cover that by UTs
	public DeliveryOrderResponseDTO delete(String clientId, String id) {
		Optional<DeliveryOrder> found = deliveryRepository.findById(id);
		if (!found.isPresent()) {
			throw new NotFoundException("No such delivery order");
		}
		DeliveryOrder existing = found.get();
		if(!existing.getClient().equals(clientId)) {
			throw new BadRequestException("No such delivery order");
		}
		
		if(existing.getStatus().ordinal() > DeliveryStatus.ASSIGNED.ordinal()) {
			throw new BadRequestException("You cannot cancel this order");
		}
		
		existing.setStatus(DeliveryStatus.CANCELED);
		DeliveryOrder result = deliveryRepository.save(existing);
		log.debug("Delivery cancellerd: {}", result);
		messageTransmitter.orderCancelled(from(result).to(OrderCancelled.class));
		log.debug("Delivery cancelation posted to queues: {}", result);
		
		return from(existing).to(DeliveryOrderResponseDTO.class);
	}



	public DeliveryOrderResponseDTO findOne(String clientId, String id) {
		Optional<DeliveryOrder> result = deliveryRepository.findByIdAndClient(id, clientId);
		if(!result.isPresent()) {
			throw new NotFoundException("There is no such delivery order");
		}
		DeliveryOrderResponseDTO order = from(result.get()).to(DeliveryOrderResponseDTO.class);
		return order;
	}



	@Transactional
	public void deliveryAssigned(OrderAssignment payload) {
		Optional<DeliveryOrder> existing = deliveryRepository.findById(payload.getId());
		if(!existing.isPresent()) {
			log.debug("Order not found {}", payload.getId());
		}
		DeliveryOrder order = existing.get();
		order.setStatus(DeliveryStatus.ASSIGNED);
		
		deliveryRepository.save(order);
	}



	@Transactional
	public void updateLocation(LocationChanged payload) {
		Optional<DeliveryOrder> existing = deliveryRepository.findById(payload.getId());
		if(!existing.isPresent()) {
			log.debug("Order not found {}", payload.getId());
		}
		
		DeliveryOrder order = existing.get();
		order.setLocation(payload.getLocation());
		order.setStatus(DeliveryStatus.INPROGRESS);
		deliveryRepository.save(order);
	}

}
