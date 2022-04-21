package com.stas.parceldelivery.publcapi.rest.client;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.commons.constants.PublicApiRoutes;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.publcapi.rest.BaseController;
import com.stas.parceldelivery.publcapi.service.ClientServiceClient;

@RestController
@RequestMapping(PublicApiRoutes.CLIENT_DELIVERIES)
public class ClientController extends BaseController {
	
	@Autowired
	ClientServiceClient clientService;

	@PostMapping(ALL)
	public DeliveryOrderResponseDTO create(@RequestBody DeliveryOrderRequestDTO d) {
		return call(c -> clientService.create(c.getUserId(), d));
	}

	@PutMapping(SINGLE)
	public DeliveryOrderResponseDTO updateDestination(@PathVariable(P_DELIVERYID)String id, @RequestBody UpdateDestinationRequest d) {
		return call(c -> clientService.updateDestination(c.getUserId(), id, d));
	}

	@GetMapping(ALL)
	public List<DeliveryOrderResponseDTO> findAll() {
		return call(c -> clientService.findAll(c.getUserId()));
	}

	@GetMapping(STATUSLESSTHAN)
	public List<DeliveryOrderResponseDTO> findDeliveriesUpToStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesUpToStatus(c.getUserId(), status));
	}

	@GetMapping(STATUSIS)
	public List<DeliveryOrderResponseDTO> findDeliveriesByStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesByStatus(c.getUserId(), status));
	}

	@DeleteMapping(SINGLE)
	public DeliveryOrderResponseDTO delete(@PathVariable(P_DELIVERYID) String id) {
		return call(c -> clientService.dismiss(c.getUserId(), id));
	}

	@GetMapping(SINGLE)
	public DeliveryOrderResponseDTO getSingle(@PathVariable(P_DELIVERYID) String id) {
		return call(c -> clientService.getSingle(c.getUserId(), id));
	}
	

}
