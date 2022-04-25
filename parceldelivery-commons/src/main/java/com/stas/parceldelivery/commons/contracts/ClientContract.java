package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

public interface ClientContract {
	@PostMapping(GIVEN_CLIENT + DELIVERIES_ALL)
	public DeliveryOrderResponseDTO create(@PathVariable(P_CLIENTID) String clientId, @RequestBody DeliveryOrderRequestDTO d);
	
	@PutMapping(GIVEN_CLIENT + DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO updateDestination(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id, @RequestBody UpdateDestinationRequest d);

	@GetMapping(GIVEN_CLIENT + DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO getSingle(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id);
	
	@GetMapping(GIVEN_CLIENT + DELIVERIES_ALL)
	public List<DeliveryOrderResponseDTO> findAll(@PathVariable(P_CLIENTID) String clientId);

	@GetMapping(GIVEN_CLIENT + DELIVERIES_STATUSLESSTHAN)
	public List<DeliveryOrderResponseDTO> findDeliveriesUpToStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status);

	@GetMapping(GIVEN_CLIENT + DELIVERIES_STATUSIS)
	public List<DeliveryOrderResponseDTO> findDeliveriesByStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status);
	
	@DeleteMapping(GIVEN_CLIENT + DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO dismiss(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id);

}
