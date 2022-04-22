package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.UserRoutes.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;

public interface UserDetailsContract {

	@PostMapping(USER_DETAILS)
	public UserDetailsDTO update(@PathVariable(P_USER_ID) String userId, @RequestBody UserDetailsDTO d);

	@GetMapping(USER_DETAILS)
	public UserDetailsDTO getDetails(@PathVariable(P_USER_ID) String userId);
	
	

}