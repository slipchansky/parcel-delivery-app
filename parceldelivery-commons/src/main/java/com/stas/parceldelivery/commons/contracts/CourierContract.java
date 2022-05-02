package com.stas.parceldelivery.commons.contracts;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stas.parceldelivery.commons.constants.CourierRoutes;
import static com.stas.parceldelivery.commons.constants.CourierRoutes.*;
import com.stas.parceldelivery.commons.model.CourierTaskDTO;

public interface CourierContract {

	@GetMapping(COURIER_GIVEN+TASKS)
	List<CourierTaskDTO> findAllMyTasks(
			@PathVariable(CourierRoutes.COURIER_ID) String courierId);

	@GetMapping(COURIER_GIVEN+TASKS_GIVEN)
	CourierTaskDTO getTaskById(
			@PathVariable(CourierRoutes.COURIER_ID) String courierId, 
			@PathVariable(CourierRoutes.TASK_ID) String id);

	@PutMapping(COURIER_GIVEN+TASKS_GIVEN)
	CourierTaskDTO updateLocation(
			@PathVariable(CourierRoutes.COURIER_ID) String courierId, 
			@PathVariable(CourierRoutes.TASK_ID) String id, 
			@RequestParam(CourierRoutes.P_LOCATION) String location);

	@PostMapping(COURIER_GIVEN+TASKS_GIVEN)
	CourierTaskDTO acceptTask(
			@PathVariable(CourierRoutes.COURIER_ID) String courierId, 
			@PathVariable(CourierRoutes.TASK_ID) String id);

	@DeleteMapping(COURIER_GIVEN+TASKS_GIVEN)
	CourierTaskDTO finalizeTask(
			@PathVariable(CourierRoutes.COURIER_ID) String courierId, 
			@PathVariable(CourierRoutes.TASK_ID) String id);

}
