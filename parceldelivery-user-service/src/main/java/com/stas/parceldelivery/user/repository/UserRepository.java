package com.stas.parceldelivery.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.user.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	Boolean existsByEmail(String email);
	
	@Query("select u from User u join u.roles r where r = :role")
	List<User> findAllByByRole(Role role);

	boolean existsByUsernameOrEmail(String username, String email);
}
