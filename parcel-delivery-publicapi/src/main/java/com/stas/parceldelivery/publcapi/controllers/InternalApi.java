package com.stas.parceldelivery.publcapi.controllers;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.publcapi.domain.Role;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InternalApi {
	
	@GetMapping("/test")
	public String test() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String name = securityContext.getAuthentication().getName();
		Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
		return name+" : "+authorities.toString();
	}
	

	@GetMapping("/all")
	public String allAccess() {
		return "any user";
	}
	
	@GetMapping("/client")
	@PreAuthorize("hasRole('CLIENT')")
	public String client() {
		return "public API";
	}
	
	
	@GetMapping("/super")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public String superAccess() {
		return "user API";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String admminAccess() {
		return "admin API";
	}
	
	@GetMapping("/courier")
	@PreAuthorize("hasRole('COURIER')")
	public String adminAccess() {
		return "admin API";
	}
}
