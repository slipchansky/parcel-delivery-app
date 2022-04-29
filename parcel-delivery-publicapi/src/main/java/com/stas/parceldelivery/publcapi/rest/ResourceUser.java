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
import com.stas.parceldelivery.commons.model.ErrorResponse;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.publcapi.constants.GrantedTo;
import com.stas.parceldelivery.publcapi.service.UserServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
 

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
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = UserDetailsDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@PostMapping
	public UserDetailsDTO updateDetails(@RequestBody UserDetailsDTO d, HttpServletResponse httpServletResponse) {
		return call(
				(c) -> 
				userService.update(c.getUserId(), d));
	}

	@ApiOperation(value = "Get Personal User Information",
            nickname = "Get User",
            tags = "")
	@ApiResponses({
		@ApiResponse(code = 200, message="Successful Operation", response = UserDetailsDTO.class),
		@ApiResponse(code = 400, message="Bad Request", response = ErrorResponse.class),
		@ApiResponse(code = 401, message="Unauthorized", response = ErrorResponse.class),
		@ApiResponse(code = 403, message="Forbidden", response = ErrorResponse.class),
		@ApiResponse(code = 404, message="Not Found", response = ErrorResponse.class),
		@ApiResponse(code = 409, message="Conflict", response = ErrorResponse.class),
		@ApiResponse(code = 500, message="Internal Server Error", response = ErrorResponse.class)
	})
	@GetMapping
	public UserDetailsDTO get() {
		return call(
				(c) -> 
				userService.getDetails(c.getUserId()));
	}
}
