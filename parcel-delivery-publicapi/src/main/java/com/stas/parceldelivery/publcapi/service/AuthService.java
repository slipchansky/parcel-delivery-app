package com.stas.parceldelivery.publcapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.publcapi.dto.JwtResponse;
import com.stas.parceldelivery.publcapi.service.auth.UserDetailsImpl;
import com.stas.parceldelivery.publcapi.utils.JwtUtils;

@Service
public class AuthService {
	
	private final JwtUtils jwtUtils;
	
	@Autowired
	public AuthService(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	public JwtResponse createAuthenticationToken(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities()
				.stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		
		JwtResponse response = new JwtResponse(jwt, JwtResponse.TOKEN_TYPE, 
				userDetails.getUsername(),
				userDetails.getEmail(), 
				roles);
		return response;
	}

}
