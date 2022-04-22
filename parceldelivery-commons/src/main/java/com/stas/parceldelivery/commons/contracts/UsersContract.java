package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.UserRoutes.BYROLE;
import static com.stas.parceldelivery.commons.constants.UserRoutes.P_ROLE;
import static com.stas.parceldelivery.commons.constants.UserRoutes.P_USER_ID;
import static com.stas.parceldelivery.commons.constants.UserRoutes.USER;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;

public interface UsersContract {
	
	@GetMapping
	public List<UserResponseDTO> list(@RequestParam(P_ROLE) Role role);
	
	@GetMapping(USER)
	public UserDTO get(@PathVariable(P_USER_ID) String userId);
	
	@RequestMapping(value = USER, method = RequestMethod.HEAD)
	public ResponseEntity<?> exists(UserDTO user);

	@PostMapping(USER)
	public UserResponseDTO save(UserDTO user);

}
