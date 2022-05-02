package com.stas.parceldelivery.publcapi.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.commons.model.SecurityUserResponseDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.publcapi.service.UserServiceClient;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserServiceClient userServiceClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			SecurityUserResponseDTO user = userServiceClient.get(username);
			return UserSecurityDetailsImpl.fromUser(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		} 
		
	}

}
