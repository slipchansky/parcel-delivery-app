package com.stas.parceldelivery.publcapi.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.publcapi.service.UserServiceClient;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserServiceClient userServiceClient;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserDTO user = userServiceClient.get(username);
			return UserSecurityDetailsImpl.fromUser(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		} 
		
	}

}
