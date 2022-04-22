package com.stas.parceldelivery.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.client.domain.User;
import com.stas.parceldelivery.commons.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Boolean existsByEmail(String email);
	
	@Query("select u from User u join u.roles r where r = :role")
	List<User> findAllByByRole(Role role);

	boolean existsByUsernameOrEmail(String username, String email);
}
