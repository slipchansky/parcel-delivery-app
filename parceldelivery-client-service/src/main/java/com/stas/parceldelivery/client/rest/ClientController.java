package com.stas.parceldelivery.client.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.client.service.DeliveryService;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.*; 

@RestController
public class ClientController {
	
	@Autowired
	DeliveryService service;


	@PostMapping(ALL)
	public Delivery create(@PathVariable(P_CLIENTID) String clientId, @RequestBody Delivery d) {
		return service.create(clientId, d);
	}

	@PutMapping(ONE)
	public Delivery updateDestination(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id, @RequestBody UpdateDestinationRequest d) {
		return service.update(clientId, id, d);
	}

	@GetMapping(ALL)
	public List<Delivery> findAll(@PathVariable(P_CLIENTID) String clientId) {
		return service.findAll(clientId);
	}

	@GetMapping(STATUSLESSTHAN)
	public List<Delivery> findDeliveriesUpToStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status) {
		return service.findDeliveriesUpToStatus(clientId, status);
	}

	@GetMapping(STATUSIS)
	public List<Delivery> findDeliveriesByStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status) {
		return service.findDeliveriesByStatus(clientId, status);
	}
	
	@DeleteMapping(ONE)
	public Delivery delete() {
		
	}

}
