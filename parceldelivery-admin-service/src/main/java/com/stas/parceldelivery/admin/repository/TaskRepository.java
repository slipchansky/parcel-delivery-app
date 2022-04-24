package com.stas.parceldelivery.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.enums.TaskState;

@Repository
public interface TaskRepository extends CrudRepository<DeliveryTask, String> {
	List<DeliveryTask> findAllByAssigneeId(String id);
	List<DeliveryTask> findAllByStatus(DeliveryStatus status);
	List<DeliveryTask> findAllByState(TaskState new1);
	long countByAssigneeId(String id);
}
