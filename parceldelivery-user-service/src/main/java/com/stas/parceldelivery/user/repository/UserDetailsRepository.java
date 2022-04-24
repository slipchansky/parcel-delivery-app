package com.stas.parceldelivery.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.user.domain.UserDetails;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, String> {
}
