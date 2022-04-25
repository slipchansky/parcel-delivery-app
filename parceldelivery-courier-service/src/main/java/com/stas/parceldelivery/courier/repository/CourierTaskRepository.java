package com.stas.parceldelivery.courier.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.courier.domain.CourierTask;

@Repository
public interface CourierTaskRepository extends CrudRepository<CourierTask, String> {
	public List<CourierTask> findAllByCourierId(String courierId);
}
