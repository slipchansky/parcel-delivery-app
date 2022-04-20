package com.stas.parceldelivery.client.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.client.repository.DeliveryRepository;
import com.stas.parceldelivery.commons.amqp.messages.DeliveryStatusChanged;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
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
	public Delivery create(String clientId, Delivery d) {
		if(d.getId()!=null) {
			throw new BadRequestException("Your request shouldn't contain id");
		}
		d.setClient(clientId);
		d.setStatus(DeliveryStatus.CREATED);
		Delivery result = deliveryRepository.save(d);
		log.debug("New delivery created: {}", d);
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery instance posted to queues: {}", d);
		return result;
	}
	

	
	@Transactional
	public Delivery update(String clientId, String id, UpdateDestinationRequest d) {
		
		if(d.getAddressTo()==null) {
			// TODO. stas. cover by UT
			log.debug("You should send new address in request body");
			throw new BadRequestException("You should send new address in body");
			
		}
		
		Optional<Delivery> found = deliveryRepository.findById(id);
		if(!found.isPresent()) {
			log.debug("Delivery not found by id", d);
			throw new NotFoundException("There is no such delivery");
		}
		
		Delivery existing = found.get();
		
			
		if(!clientId.equals(existing.getClient())) {
			log.debug("Delivery client does not match", d);
			throw new BadRequestException("There is no such delivery");
		}
		
		if (d.getAddressTo().equals(existing.getAddressTo())) {
			// TODO. stas. cover by UT
			// nothing to do
			return existing;
		}
		
		existing.setAddressTo(d.getAddressTo());
		Delivery result = deliveryRepository.save(existing);
		log.debug("Delivery updated: {}", d);
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery instance posted to queues: {}", d);
		return result;
	}

	public List<Delivery> findAll(String clientId) {
		return deliveryRepository.findAllByClient(clientId);
	}
	
	public List<Delivery> findDeliveriesUpToStatus(String userId, DeliveryStatus toStatus) {
		return deliveryRepository.findAllByClientAndStatusLessThan(userId, toStatus);
	}
	
	public List<Delivery> findDeliveriesByStatus(String userId, DeliveryStatus status) {
		return deliveryRepository.findAllByClientAndStatus(userId, status);
	}

	@Transactional
	public void updateDeliveryStatus(DeliveryStatusChanged delta) {
		Optional<Delivery> found = deliveryRepository.findById(delta.getDeliveryId());
		if(found.isPresent()) {
		   Delivery delivery = found.get();
		   delivery.setStatus(delta.getNewStatus());
		   // TODO. process possible errors
		   deliveryRepository.save(delivery);
		} else {
		  // TODO. implement what then
		}
	}
	
	@Transactional
	// FIXME. stas. cover that by UTs
	public Delivery delete(String clientId, String id) {
		Optional<Delivery> found = deliveryRepository.findById(id);
		if (!found.isPresent()) {
			throw new NotFoundException("No such delivery order");
		}
		Delivery existing = found.get();
		if(!existing.getClient().equals(clientId)) {
			throw new BadRequestException("No such delivery order");
		}
		
		if(existing.getStatus().ordinal() > DeliveryStatus.ASSIGNED.ordinal()) {
			throw new BadRequestException("You cannot cancel this order");
		}
		
		existing.setStatus(DeliveryStatus.CANCELED);
		Delivery result = deliveryRepository.save(existing);
		log.debug("Delivery cancellerd: {}", result);
		messageTransmitter.deliveryUpdated(result);
		log.debug("Delivery cancelation posted to queues: {}", result);
		
		return existing;
	}

}
