package com.stas.parceldelivery.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.client.domain.DeliveryOrder;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliveryOrder, String> {

	@Query("select d from DeliveryOrder d where d.client=:client and d.status<:status")
	public List<DeliveryOrder> findAllByClientAndStatusLessThan(String client, DeliveryStatus status);

	public List<DeliveryOrder> findAllByClientAndStatus(String client, DeliveryStatus status);

	public List<DeliveryOrder> findAllByClient(String userId);

	public Optional<DeliveryOrder> findByIdAndClient(String id, String clientId);
}
