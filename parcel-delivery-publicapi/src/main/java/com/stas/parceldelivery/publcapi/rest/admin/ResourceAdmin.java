package com.stas.parceldelivery.publcapi.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.stas.parceldelivery.publcapi.rest.BaseController;
import com.stas.parceldelivery.publcapi.service.AdminServiceClient;

@RestController
@RequestMapping(PublicApiRoutes.ADMIN_ALL)
public class ResourceAdmin extends BaseController {
	
	@Autowired
	AdminServiceClient adminService;

	@PostMapping(AdminRoutes.COURIER_GIVEN)
	public CourierDTO registerCourier(@PathVariable(AdminRoutes.P_COURIER_ID) String userId) {
		return call(c -> adminService.registerCourier(userId)) ;
	}

	public List<CourierDTO> getAllCouriers() {
		return call(c -> adminService.getAllCouriers());
	}

	public List<CourierDTO> getFreeCouriers() {
		return call(c -> adminService.getFreeCouriers());
	}

	public List<CourierDTO> getBusyCouriers() {
		return call(c -> adminService.getBusyCouriers());
	}

	public List<DeliveryTaskDTO> getAllTasks() {
		return call(c -> adminService.getAllTasks());
	}

	public List<DeliveryTaskDTO> getUnassignedTasks() {
		return call(c -> adminService.getUnassignedTasks());
	}

	@PutMapping(AdminRoutes.TASK_ASSIGN)
	public DeliveryTaskDTO assignTask(@PathVariable(AdminRoutes.P_TASK_ID) String taskId, @PathVariable(AdminRoutes.P_COURIER_ID) String courioerId) {
		return call(c -> adminService.assignTask(taskId, courioerId));
	}

	@PutMapping(AdminRoutes.TASK_GIVEN)
	public DeliveryTaskDetailsDTO getTaskDetails(@PathVariable(AdminRoutes.P_TASK_ID) String taskId) {
		return call(c -> adminService.getTaskDetails(taskId));
	}
}
