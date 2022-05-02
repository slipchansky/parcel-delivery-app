package com.stas.parceldelivery.user.rest;

import static com.stas.parceldelivery.commons.constants.UserRoutes.ROOT;

import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.commons.contracts.UsersContract;
import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.model.NewUserRequestDTO;
import com.stas.parceldelivery.commons.model.SecurityUserResponseDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.user.service.UserDetailsService;
import com.stas.parceldelivery.user.service.UserService;

@RestController
@RequestMapping(ROOT)
public class UsersController implements UsersContract {

	@Autowired
	UserService uservice;
	
	@Autowired
	UserDetailsService service;
	
	@Autowired
	Validator validator;
	
	

	@Override
	public UserDetailsDTO update(String userId, UserDetailsDTO d) {
		return service.update(userId, d);
	}
	
	@Override
	public UserDetailsDTO getDetails(String userId) {
		return service.get(userId);
	}
	
	
	@Override
	public List<UserResponseDTO> list(Role role) {
		return uservice.listByRole(role);
	}


	@Override
	public ResponseEntity<?> headOfUserExists(String username, String email) {
		if(uservice.exists(username, email)) return ResponseEntity.noContent().build();
		return ResponseEntity.notFound().build();
	}

	@Override
	public UserResponseDTO save(NewUserRequestDTO user) {
		return uservice.create(user);
	}

	@Override
	public SecurityUserResponseDTO get(String userId) {
		return uservice.findById(userId);
	}

}
