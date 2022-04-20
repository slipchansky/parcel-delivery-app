package com.stas.parceldelivery.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.client.repository.DeliveryRepository;
import com.stas.parceldelivery.commons.amqp.messages.DeliveryStatusChanged;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.DeliveryOrderDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryService {
	
	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Autowired
	ClientMessageTransmitter messageTransmitter;
	
	
	
	@Transactional
	public DeliveryOrderDTO create(String clientId, DeliveryOrderDTO d) {
		if(d.getId()!=null) {
			throw new BadRequestException("Your request shouldn't contain id");
		}
		d.setClient(clientId);
		d.setStatus(DeliveryStatus.CREATED);
		
		DeliveryOrder result = deliveryRepository.save(DeliveryOrder.fromDto(d));
		log.debug("New delivery created: {}", d);
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery instance posted to queues: {}", d);
		return result.toDto();
	}
	

	
	@Transactional
	public DeliveryOrderDTO update(String clientId, String id, UpdateDestinationRequest d) {
		
		if(d.getAddressTo()==null) {
			// TODO. stas. cover by UT
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
			return existing.toDto();
		}
		
		existing.setAddressTo(d.getAddressTo());
		DeliveryOrder result = deliveryRepository.save(existing);
		log.debug("Delivery updated: {}", d);
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery instance posted to queues: {}", d);
		return result.toDto();
	}

	public List<DeliveryOrderDTO> findAll(String clientId) {
		return deliveryRepository.findAllByClient(clientId).stream().map(DeliveryOrder::toDto).collect(Collectors.toList());
	}
	
	public List<DeliveryOrderDTO> findDeliveriesUpToStatus(String userId, DeliveryStatus toStatus) {
		return deliveryRepository.findAllByClientAndStatusLessThan(userId, toStatus).stream().map(DeliveryOrder::toDto).collect(Collectors.toList());
	}
	
	public List<DeliveryOrderDTO> findDeliveriesByStatus(String userId, DeliveryStatus status) {
		return deliveryRepository.findAllByClientAndStatus(userId, status).stream().map(DeliveryOrder::toDto).collect(Collectors.toList());
	}

	@Transactional
	public void updateDeliveryStatus(DeliveryStatusChanged delta) {
		Optional<DeliveryOrder> found = deliveryRepository.findById(delta.getDeliveryId());
		if(found.isPresent()) {
		   DeliveryOrder delivery = found.get();
		   delivery.setStatus(delta.getNewStatus());
		   // TODO. process possible errors
		   deliveryRepository.save(delivery);
		} else {
		  // TODO. implement what then
		}
	}
	
	@Transactional
	// FIXME. stas. cover that by UTs
	public DeliveryOrderDTO delete(String clientId, String id) {
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
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery cancelation posted to queues: {}", result);
		
		return existing.toDto();
	}

}
