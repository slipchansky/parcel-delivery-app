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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.client.service.DeliveryService;
import com.stas.parceldelivery.commons.contracts.ClientContract;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.*; 

@RestController
@RequestMapping(ROOT)
public class ClientController implements ClientContract {
	
	@Autowired
	DeliveryService service;


	public DeliveryOrderResponseDTO create(String clientId, DeliveryOrderRequestDTO d) {
		return service.create(clientId, d);
	}
	
	public DeliveryOrderResponseDTO updateDestination(String clientId, String id, UpdateDestinationRequest d) {
		return service.update(clientId, id, d);
	}

	public List<DeliveryOrderResponseDTO> findAll(String clientId) {
		return service.findAll(clientId);
	}

	public List<DeliveryOrderResponseDTO> findDeliveriesUpToStatus(String clientId, DeliveryStatus status) {
		return service.findDeliveriesUpToStatus(clientId, status);
	}

	public List<DeliveryOrderResponseDTO> findDeliveriesByStatus(String clientId, DeliveryStatus status) {
		return service.findDeliveriesByStatus(clientId, status);
	}
	
	public DeliveryOrderResponseDTO dismiss(String clientId, String id) {
		return service.delete(clientId, id);
	}

	@Override
	public DeliveryOrderResponseDTO getSingle(String clientId, String id) {
		return service.findOne(clientId, id);
	}


}
