package com.stas.parceldelivery.commons.contracts;

import static com.stas.parceldelivery.commons.constants.UserRoutes.P_USER_ID;
import static com.stas.parceldelivery.commons.constants.UserRoutes.USER;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stas.parceldelivery.commons.model.UserDetailsDTO;

public interface UserDetailsContract {

	@PostMapping(USER)
	public UserDetailsDTO update(@PathVariable(P_USER_ID) String userId, @RequestBody UserDetailsDTO d);

	@GetMapping(USER)
	public UserDetailsDTO get(@PathVariable(P_USER_ID) String userId);

}