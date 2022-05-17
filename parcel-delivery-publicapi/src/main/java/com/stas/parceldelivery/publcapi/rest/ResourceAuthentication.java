package com.stas.parceldelivery.publcapi.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.commons.model.NewUserRequestDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.publcapi.dto.JwtResponse;
import com.stas.parceldelivery.publcapi.dto.LoginRequest;
import com.stas.parceldelivery.publcapi.service.AuthService;
import com.stas.parceldelivery.publcapi.service.UserService;
import com.stas.parceldelivery.publcapi.service.auth.UserDetailsImpl;
import com.stas.parceldelivery.publcapi.utils.JwtUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Authentication API")
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResourceAuthentication extends BaseController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;
	
	
	@Autowired
	AuthService authService;


	@ApiOperation(value = "User Login API",
            nickname = "Login",
            tags = "")
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authUser(@RequestBody LoginRequest loginRequest) {
		return call(c -> {
			
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), 
							loginRequest.getPassword())
			);
			return ResponseEntity.ok(authService.createAuthenticationToken(authentication));

		});
	}


	@ApiOperation(value = "User Signup API",
            nickname = "Signup",
            tags = "")
	@PostMapping("/signup")
	public UserResponseDTO registerClient(@RequestBody @Valid NewUserRequestDTO signupRequest) {
		return call(c -> userService.registerUser(signupRequest));
	}

}
