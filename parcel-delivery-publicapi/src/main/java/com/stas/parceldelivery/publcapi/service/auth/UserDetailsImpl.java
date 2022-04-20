package com.stas.parceldelivery.publcapi.service.auth;

import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stas.parceldelivery.publcapi.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private String username;
	private String email;
	
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public static UserDetailsImpl fromUser(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.toString()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getUsername(), 
				user.getEmail(), 
				user.getPassword(), 
				authorities);
	}
	

}
