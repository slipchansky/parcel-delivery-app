package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.UserRoutes.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;

public interface UsersContract {
	
	@GetMapping
	public List<UserResponseDTO> list(@RequestParam(P_ROLE) Role role);
	
	@GetMapping(USER)
	public UserDTO get(@PathVariable(P_USER_ID) String userId);
	
	@RequestMapping(value = USERS, method = RequestMethod.HEAD)
	public ResponseEntity<?> headOfUserExists(@RequestParam(P_USER_ID) String userId, @RequestParam(P_USER_EMAIL) String email);

	@PostMapping(USERS)
	public UserResponseDTO save(@RequestBody UserDTO user);
	
	@PostMapping(USER_DETAILS)
	public UserDetailsDTO update(@PathVariable(P_USER_ID) String userId, @RequestBody UserDetailsDTO d);

	@GetMapping(USER_DETAILS)
	public UserDetailsDTO getDetails(@PathVariable(P_USER_ID) String userId);
	

}
