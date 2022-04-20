package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.ALL;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.P_CLIENTID;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.P_DELIVERYID;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.P_STATUS;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.SINGLE;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.STATUSIS;
import static com.stas.parceldelivery.commons.constants.ClientRoutes.STATUSLESSTHAN;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;

public interface ClientContract {
	@PostMapping(ALL)
	public DeliveryOrderDTO create(@PathVariable(P_CLIENTID) String clientId, @RequestBody DeliveryOrderDTO d);

	@PutMapping(SINGLE)
	public DeliveryOrderDTO updateDestination(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id, @RequestBody UpdateDestinationRequest d);

	@GetMapping(ALL)
	public List<DeliveryOrderDTO> findAll(@PathVariable(P_CLIENTID) String clientId);

	@GetMapping(STATUSLESSTHAN)
	public List<DeliveryOrderDTO> findDeliveriesUpToStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status);

	@GetMapping(STATUSIS)
	public List<DeliveryOrderDTO> findDeliveriesByStatus(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_STATUS) DeliveryStatus status);
	
	@DeleteMapping(SINGLE)
	public DeliveryOrderDTO dismiss(@PathVariable(P_CLIENTID) String clientId, @PathVariable(P_DELIVERYID) String id);

}
