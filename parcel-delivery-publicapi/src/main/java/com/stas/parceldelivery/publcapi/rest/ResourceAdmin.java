package com.stas.parceldelivery.publcapi.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.commons.constants.AdminRoutes;
import com.stas.parceldelivery.commons.constants.PublicApiRoutes;
import com.stas.parceldelivery.commons.model.CourierDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDetailsDTO;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.AdminServiceClient;
import io.swagger.annotations.*;

@Api(value = "Administrator's API")
@RestController
@RequestMapping(PublicApiRoutes.ADMIN)
@PreAuthorize(GrantedTo.ADMIN)
public class ResourceAdmin extends BaseController {
	
	@Autowired
	AdminServiceClient adminService;

	@ApiOperation(value = "Register existing user as a Courier",
            nickname = "Register New Courier",
            notes = "User should have an ROLE_COURIER authority",
            tags = "")
	@PostMapping(AdminRoutes.COURIER_GIVEN)
	public CourierDTO registerCourier(@PathVariable(AdminRoutes.P_COURIER_ID) String userId) {
		return call(c -> adminService.registerCourier(userId)) ;
	}

	@ApiOperation(value = "Get all registered Couriers",
            nickname = "Get Couriers",
            tags = "")
	@GetMapping(AdminRoutes.COURIERS)
	public List<CourierDTO> getAllCouriers() {
		return call(c -> adminService.getAllCouriers());
	}

	@ApiOperation(value = "List couriers that are free",
            nickname = "Free Couriers",
            tags = "")
	@GetMapping(AdminRoutes.COURIERS_FREE)
	public List<CourierDTO> getFreeCouriers() {
		return call(c -> adminService.getFreeCouriers());
	}

	@ApiOperation(value = "List couriers that are busy",
            nickname = "Busy Couriers",
            tags = "")
	@GetMapping(AdminRoutes.COURIERS_BUSY)
	public List<CourierDTO> getBusyCouriers() {
		return call(c -> adminService.getBusyCouriers());
	}

	@ApiOperation(value = "List all available tasks",
            nickname = "All Tasks",
            tags = "")
	@GetMapping(AdminRoutes.TASKS)
	public List<DeliveryTaskDTO> getAllTasks() {
		return call(c -> adminService.getAllTasks());
	}

	@ApiOperation(value = "List Unassigned Tasks",
			notes = "All the Tasks that are not assigned to Courier",
            nickname = "All Tasks",
            tags = "")
	@GetMapping(AdminRoutes.TASKS_UNASSIGNED)
	public List<DeliveryTaskDTO> getUnassignedTasks() {
		return call(c -> adminService.getUnassignedTasks());
	}

	@ApiOperation(value = "Assign the task to Courier",
            nickname = "Assign Task",
            tags = "")
	@PutMapping(AdminRoutes.TASK_ASSIGN)
	public DeliveryTaskDTO assignTask(@PathVariable(AdminRoutes.P_TASK_ID) String taskId, @PathVariable(AdminRoutes.P_COURIER_ID) String courioerId) {
		return call(c -> adminService.assignTask(taskId, courioerId));
	}

	@ApiOperation(value = "Get Task Details",
			notes = "Return task and the Task Tracking History",
            nickname = "Task Details",
            tags = "")
	@GetMapping(AdminRoutes.TASK_GIVEN)
	public DeliveryTaskDetailsDTO getTaskDetails(@PathVariable(AdminRoutes.P_TASK_ID) String taskId) {
		return call(c -> adminService.getTaskDetails(taskId));
	}
}
