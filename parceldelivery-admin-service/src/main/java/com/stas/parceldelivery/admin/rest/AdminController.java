package com.stas.parceldelivery.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.admin.service.CourierService;
import com.stas.parceldelivery.admin.service.TaskService;
import com.stas.parceldelivery.commons.enums.CourierStatus;
import com.stas.parceldelivery.commons.model.CourierDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDTO;
import com.stas.parceldelivery.commons.model.DeliveryTaskDetailsDTO;
import com.stas.parceldelivery.commons.contracts.AdminContract;

import static com.stas.parceldelivery.commons.constants.AdminRoutes.ROOT; 

@RestController
@RequestMapping(ROOT)
public class AdminController implements AdminContract {
	
	@Autowired
	private CourierService couriers;
	
	@Autowired
	private TaskService tasks;

	@Override
	public CourierDTO registerCourier(String userId) {
		return couriers.registerCourier(userId);
	}

	@Override
	public List<CourierDTO> getAllCouriers() {
		return couriers.getAllCouriers();
	}

	@Override
	public List<CourierDTO> getFreeCouriers() {
		return couriers.getCouriers(CourierStatus.free);
	}

	@Override
	public List<CourierDTO> getBusyCouriers() {
		return couriers.getCouriers(CourierStatus.busy);
	}

	@Override
	public List<DeliveryTaskDTO> getAllTasks() {
		return tasks.getAllTasks();
	}

	@Override
	public List<DeliveryTaskDTO> getUnassignedTasks() {
		return tasks.getUnassigned();
	}

	@Override
	public DeliveryTaskDTO assignTask(String taskId, String courierId) {
		return tasks.assignTask(taskId, courierId);
	}

	@Override
	public DeliveryTaskDetailsDTO getTaskDetails(String taskId) {
		return tasks.getTaskDetails(taskId);
	}


}
