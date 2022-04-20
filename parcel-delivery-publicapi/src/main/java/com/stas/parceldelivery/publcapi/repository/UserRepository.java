package com.stas.parceldelivery.publcapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.publcapi.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	//Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
