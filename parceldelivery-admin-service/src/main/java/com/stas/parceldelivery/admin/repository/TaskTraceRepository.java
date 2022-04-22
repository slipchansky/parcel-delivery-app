package com.stas.parceldelivery.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.admin.domain.DeliveryTaskTrace;

@Repository
public interface TaskTraceRepository extends CrudRepository<DeliveryTaskTrace, String>{
	
	public List<DeliveryTaskTrace> findAllByTaskId(String id);

}
