package com.stas.parceldelivery.publcapi.resources.client;

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

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.DeliveryOrderDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.publcapi.resources.BaseController;
import com.stas.parceldelivery.publcapi.service.ClientServiceClient;

@RestController
@RequestMapping("/deliveries")
public class ClientController extends BaseController {
	
	@Autowired
	ClientServiceClient clientService;

	@PostMapping(ALL)
	public DeliveryOrderDTO create(@RequestBody DeliveryOrderDTO d) {
		return call(c -> clientService.create(c.getUserId(), d));
	}

	@PutMapping(SINGLE)
	public DeliveryOrderDTO updateDestination(@PathVariable(P_DELIVERYID)String id, @RequestBody UpdateDestinationRequest d) {
		return call(c -> clientService.updateDestination(c.getUserId(), id, d));
	}

	@GetMapping(ALL)
	public List<DeliveryOrderDTO> findAll() {
		return call(c -> clientService.findAll(c.getUserId()));
	}

	@GetMapping(STATUSLESSTHAN)
	public List<DeliveryOrderDTO> findDeliveriesUpToStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesUpToStatus(c.getUserId(), status));
	}

	@GetMapping(STATUSIS)
	public List<DeliveryOrderDTO> findDeliveriesByStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesByStatus(c.getUserId(), status));
	}

	@DeleteMapping(SINGLE)
	public DeliveryOrderDTO delete(@PathVariable(P_DELIVERYID) String id) {
		return call(c -> clientService.dismiss(c.getUserId(), id));
	}

}
