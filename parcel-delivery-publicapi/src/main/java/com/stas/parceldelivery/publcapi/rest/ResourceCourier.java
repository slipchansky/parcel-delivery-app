package com.stas.parceldelivery.publcapi.rest;

import static com.stas.parceldelivery.commons.constants.CourierRoutes.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.commons.model.CourierTaskDTO;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.CourierServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Courier API")
@RestController
@RequestMapping(ROOT)
@PreAuthorize(GrantedTo.COURIER)
public class ResourceCourier extends BaseController {
	
	@Autowired
	private CourierServiceClient service;

	
	@ApiOperation(value = "List Tasks assigned to given Courier",
            nickname = "List Tasks",
            tags = "")
	@GetMapping(TASKS)
	public List<CourierTaskDTO> findAllMyTasks() {
		return call(c -> service.findAllMyTasks(c.getUserId()));
	}

	@ApiOperation(value = "Get Task Details",
            nickname = "Get Task Details")
	@GetMapping(TASKS_GIVEN)
	public CourierTaskDTO getTaskById(@PathVariable(TASK_ID) String id) {
		return call(c -> service.getTaskById(c.getUserId(), id));
	}

	@ApiOperation(value = "Update Parcell location for given Order",
            nickname = "Update Location",
            tags = "")
	@PutMapping(TASKS_GIVEN)
	public CourierTaskDTO updateLocation(@PathVariable(TASK_ID) String id, @RequestParam(P_LOCATION) String location) {
		return call(c -> service.updateLocation(c.getUserId(), id, location));
	}

	@ApiOperation(value = "Start tracking Parcell",
            nickname = "Start Task",
            tags = "")
	@PostMapping(TASKS_GIVEN)
	public CourierTaskDTO startDeliverying(@PathVariable(TASK_ID) String id) {
		return call(c -> service.startDeliverying(c.getUserId(), id));
	}

	@ApiOperation(value = "Finalize Parcel Delivery by Tracking Number",
            nickname = "Finalize Task",
            tags = "")
	@DeleteMapping(TASKS_GIVEN)
	public CourierTaskDTO finishDeliverying(@PathVariable(TASK_ID) String id) {
		return call(c -> service.finishDeliverying(c.getUserId(), id));
	}

}
