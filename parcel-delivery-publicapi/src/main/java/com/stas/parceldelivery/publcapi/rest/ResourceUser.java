package com.stas.parceldelivery.publcapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

import com.stas.parceldelivery.commons.constants.PublicApiRoutes;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.UserServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 

@Api(value = "User API")
@RestController
@RequestMapping(PublicApiRoutes.USERS)
@PreAuthorize(GrantedTo.ANY)
public class ResourceUser extends BaseController {
	
	@Autowired
	UserServiceClient userService;

	@ApiOperation(value = "Update Personal User Information",
            nickname = "Update User",
            tags = "")
	@PostMapping
	@PreAuthorize(GrantedTo.ANY)
	public UserDetailsDTO updateDetails(@RequestBody UserDetailsDTO d, HttpServletResponse httpServletResponse) {
		return call(
				(c) -> 
				userService.update(c.getUserId(), d));
	}

	@ApiOperation(value = "Get Personal User Information",
            nickname = "Get User",
            tags = "")
	@GetMapping
	@PreAuthorize(GrantedTo.ANY)
	public UserDetailsDTO get() {
		return call(
				(c) -> 
				userService.getDetails(c.getUserId()));
	}

}
