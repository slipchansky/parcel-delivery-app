package com.stas.parceldelivery.publcapi.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.stas.parceldelivery.commons.constants.UserRoutes.*;

import javax.servlet.http.HttpServletResponse;

import com.stas.parceldelivery.commons.constants.PublicApiRoutes;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.rest.BaseController;
import com.stas.parceldelivery.publcapi.service.UserDetailsServiceClient;
 

@RestController
@RequestMapping(PublicApiRoutes.USERS)
public class ResourceUser extends BaseController {
	
	@Autowired
	UserDetailsServiceClient userService;

	@PostMapping
	@PreAuthorize(GrantedTo.ANY)
	public UserDetailsDTO updateDetails(@RequestBody UserDetailsDTO d, HttpServletResponse httpServletResponse) {
		return call(
				(c) -> 
				userService.update(c.getUserId(), d));
	}

	@GetMapping
	@PreAuthorize(GrantedTo.ANY)
	public UserDetailsDTO get() {
		return call(
				(c) -> 
				userService.getDetails(c.getUserId()));
	}

}
