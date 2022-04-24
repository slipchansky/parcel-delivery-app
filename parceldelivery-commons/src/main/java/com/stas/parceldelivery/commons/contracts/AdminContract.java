package com.stas.parceldelivery.commons.contracts;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.stas.parceldelivery.commons.constants.AdminRoutes;
import com.stas.parceldelivery.commons.model.CourierDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDetailsDTO;

public interface AdminContract {
	
	@PostMapping(AdminRoutes.COURIER_GIVEN)
	public CourierDTO registerCourier(@PathVariable(AdminRoutes.P_COURIER_ID) String userId);
	
	@GetMapping(AdminRoutes.COURIERS)
	public List<CourierDTO> getAllCouriers();
	
	@GetMapping(AdminRoutes.COURIERS_FREE)
	public List<CourierDTO> getFreeCouriers();
	
	@GetMapping(AdminRoutes.COURIERS_BUSY)
	public List<CourierDTO> getBusyCouriers();
	
	
	@GetMapping(AdminRoutes.TASKS)
	public List<DeliveryTaskDTO> getAllTasks();
	
	@GetMapping(AdminRoutes.TASKS_UNASSIGNED)
	public List<DeliveryTaskDTO> getUnassignedTasks();
	
	@GetMapping(AdminRoutes.TASK_ASSIGN)
	public DeliveryTaskDTO assignTask(@PathVariable(AdminRoutes.P_TASK_ID) String taskId, @PathVariable(AdminRoutes.P_COURIER_ID) String courioerId);
	
	@GetMapping(AdminRoutes.TASK_GIVEN)
	public DeliveryTaskDetailsDTO getTaskDetails(@PathVariable(AdminRoutes.P_TASK_ID) String taskId);
	

}
