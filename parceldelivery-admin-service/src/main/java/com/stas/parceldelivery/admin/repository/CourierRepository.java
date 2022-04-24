package com.stas.parceldelivery.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.commons.enums.CourierStatus;

@Repository
public interface CourierRepository extends CrudRepository<Courier, String> {

	List<Courier> findAllByStatus(CourierStatus free);
}
