package com.stas.parceldelivery.publcapi.dto;

import java.util.Set;

import com.stas.parceldelivery.publcapi.domain.Role;
import com.stas.parceldelivery.publcapi.domain.User;

import lombok.Data;

@Data
public class SignupRequest {
	private String username;
	private String email;
	private Set<Role> roles;
	private String password;
	
	
	public User toDomainUser() {
		return User.builder()
				.username(username)
				.email(email)
				.password(password)
				.roles(roles)
				.build();
	}
	
}
