package com.stas.parceldelivery.publcapi.rest;

import static com.stas.parceldelivery.commons.constants.ClientRoutes.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.ClientServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Client API")
@RestController
@RequestMapping(PublicApiRoutes.ROOT+PublicApiRoutes.CLIENT)
@PreAuthorize(GrantedTo.CLIENT)
public class ResourceClient extends BaseController {
	
	@Autowired
	ClientServiceClient clientService;

	@ApiOperation(value = "Create new Delivery Order",
            nickname = "New Order",
            tags = "")
	@PostMapping(DELIVERIES_ALL)
	public DeliveryOrderResponseDTO create(@RequestBody DeliveryOrderRequestDTO d) {
		return call(c -> clientService.create(c.getUserId(), d));
	}

	@ApiOperation(value = "Update existing Order with new Destination address",
            nickname = "Update Destination Address",
            tags = "")
	@PutMapping(DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO updateDestination(@PathVariable(P_DELIVERYID)String id, @RequestBody UpdateDestinationRequest d) {
		return call(c -> clientService.updateDestination(c.getUserId(), id, d));
	}

	@ApiOperation(value = "List all Orders for given Client",
            nickname = "List Orders",
            tags = "")
	@GetMapping(DELIVERIES_ALL)
	public List<DeliveryOrderResponseDTO> findAll() {
		return call(c -> clientService.findAll(c.getUserId()));
	}

	@ApiOperation(value = "Find Delivery Orders that didn reach to given Status",
            nickname = "List Orders up to Status",
            tags = "")
	@GetMapping(DELIVERIES_STATUSLESSTHAN)
	public List<DeliveryOrderResponseDTO> findDeliveriesUpToStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesUpToStatus(c.getUserId(), status));
	}

	@ApiOperation(value = "Find Delivery Orders that have given Status",
            nickname = "List Orders by Status",
            tags = "")
	@GetMapping(DELIVERIES_STATUSIS)
	public List<DeliveryOrderResponseDTO> findDeliveriesByStatus(@PathVariable(P_STATUS) DeliveryStatus status) {
		return call(c -> clientService.findDeliveriesByStatus(c.getUserId(), status));
	}

	@ApiOperation(value = "Delete given Order",
            nickname = "Delete Order",
            tags = "")
	@DeleteMapping(DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO delete(@PathVariable(P_DELIVERYID) String id) {
		return call(c -> clientService.dismiss(c.getUserId(), id));
	}

	@ApiOperation(value = "Get details for given Order",
            nickname = "Get Order",
            tags = "")
	@GetMapping(DELIVERIES_SINGLE)
	public DeliveryOrderResponseDTO getSingle(@PathVariable(P_DELIVERYID) String id) {
		return call(c -> clientService.getSingle(c.getUserId(), id));
	}
	

}
