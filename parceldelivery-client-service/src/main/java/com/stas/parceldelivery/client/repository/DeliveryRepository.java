package com.stas.parceldelivery.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, String> {

	@Query("select d from Delivery d where d.client=:client and d.status<:status")
	public List<Delivery> findAllByClientAndStatusLessThan(String client, DeliveryStatus status);

	public List<Delivery> findAllByClientAndStatus(String client, DeliveryStatus status);

	public List<Delivery> findAllByClient(String userId);
}
