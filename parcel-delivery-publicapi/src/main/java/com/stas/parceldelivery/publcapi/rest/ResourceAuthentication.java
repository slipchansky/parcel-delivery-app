package com.stas.parceldelivery.publcapi.rest;

import java.util.List;
import java.util.stream.Collectors;

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

import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.publcapi.config.jwt.JwtUtils;
import com.stas.parceldelivery.publcapi.dto.JwtResponse;
import com.stas.parceldelivery.publcapi.dto.LoginRequest;
import com.stas.parceldelivery.publcapi.service.UserService;
import com.stas.parceldelivery.publcapi.service.auth.UserSecurityDetailsImpl;

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
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authUser(@RequestBody LoginRequest loginRequest) {
		return call(c -> {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserSecurityDetailsImpl userDetails = (UserSecurityDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, JwtResponse.TOKEN_TYPE, userDetails.getUsername(),
					userDetails.getEmail(), roles));

		});
	}


	@PostMapping("/signup")
	public UserResponseDTO registerClient(@RequestBody UserDTO signupRequest) {
		return call(c -> userService.createClient(signupRequest));
	}

}