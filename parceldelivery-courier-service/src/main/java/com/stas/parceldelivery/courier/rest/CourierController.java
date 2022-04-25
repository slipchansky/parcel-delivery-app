package com.stas.parceldelivery.courier.rest;

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

import com.stas.parceldelivery.commons.contracts.ClientContract;
import com.stas.parceldelivery.commons.contracts.CourierContract;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.CourierTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderRequestDTO;
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.courier.domain.CourierTask;
import com.stas.parceldelivery.courier.service.CourierService;

import static com.stas.parceldelivery.commons.constants.CourierRoutes.*; 

@RestController
@RequestMapping(ROOT)
public class CourierController implements CourierContract {
	
	@Autowired
	CourierService service;

	@Override
	public List<CourierTaskDTO> findAllMyTasks(String courierId) {
		return service.findAllMyTasks(courierId);
	}

	@Override
	public CourierTaskDTO getTaskById(String courierId, String id) {
		return service.getTaskById(courierId, id);
	}

	@Override
	public CourierTaskDTO updateLocation(String courierId, String id, String location) {
		return service.updateLocation(courierId, id, location);
	}

	@Override
	public CourierTaskDTO startDeliverying(String courierId, String id) {
		return service.startDeliverying(courierId, id);
	}

	@Override
	public CourierTaskDTO finishDeliverying(String courierId, String id) {
		return service.finishDeliverying(courierId, id);
	}
	
	

	
	
	
	


}
