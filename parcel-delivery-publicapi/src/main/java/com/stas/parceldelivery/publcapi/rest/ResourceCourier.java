package com.stas.parceldelivery.publcapi.rest;

import static com.stas.parceldelivery.commons.constants.CourierRoutes.*;

import java.util.List;

import javax.annotation.security.RolesAllowed;

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
import com.stas.parceldelivery.commons.model.DeliveryOrderResponseDTO;
import com.stas.parceldelivery.commons.model.ErrorResponse;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.CourierServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = CourierTaskDTO.class, responseContainer = "List"),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@GetMapping(TASKS)
	public List<CourierTaskDTO> findAllMyTasks() {
		return call(c -> service.findAllMyTasks(c.getUserId()));
	}

	@ApiOperation(value = "Get Task Details",
            nickname = "Get Task Details")
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = CourierTaskDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@GetMapping(TASKS_GIVEN)
	public CourierTaskDTO getTaskById(@PathVariable(TASK_ID) String id) {
		return call(c -> service.getTaskById(c.getUserId(), id));
	}

	@ApiOperation(value = "Update Parcell location for given Order",
            nickname = "Update Location",
            tags = "")
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = CourierTaskDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@PutMapping(TASKS_GIVEN)
	public CourierTaskDTO updateLocation(@PathVariable(TASK_ID) String id, @RequestParam(P_LOCATION) String location) {
		return call(c -> service.updateLocation(c.getUserId(), id, location));
	}

	@ApiOperation(value = "Start tracking Parcell",
            nickname = "Start Task",
            tags = "")
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = CourierTaskDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@PostMapping(TASKS_GIVEN)
	public CourierTaskDTO startDeliverying(@PathVariable(TASK_ID) String id) {
		return call(c -> service.acceptTask(c.getUserId(), id));
	}

	@ApiOperation(value = "Finalize Parcel Delivery by Tracking Number",
            nickname = "Finalize Task",
            tags = "")
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = CourierTaskDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@DeleteMapping(TASKS_GIVEN)
	public CourierTaskDTO finishDeliverying(@PathVariable(TASK_ID) String id) {
		return call(c -> service.finalizeTask(c.getUserId(), id));
	}

}
