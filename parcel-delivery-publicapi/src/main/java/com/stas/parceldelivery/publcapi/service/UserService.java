package com.stas.parceldelivery.publcapi.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.exceptions.ErrorFromUnderlyingService;
import com.stas.parceldelivery.commons.model.NewUserRequestDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.publcapi.exceptions.InternalServerErrorRuntimeException;
import com.stas.parceldelivery.publcapi.exceptions.SilentExceptionWrapper;
import com.stas.parceldelivery.publcapi.exceptions.UserAlreadyExistsException;

@Service
public class UserService {
	
	@Autowired
	UserServiceClient userServiceClient;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	
	public UserResponseDTO registerUser(NewUserRequestDTO u) throws UserAlreadyExistsException {
		Set<Role> roles = u.getRoles();
		roles.remove(Role.ROLE_SUPER_ADMIN);
		return createUser(u);
	}
	
	private UserResponseDTO createUser(NewUserRequestDTO userdto) throws UserAlreadyExistsException {
		if(userExists( userdto.getUsername(), userdto.getEmail()))
			throw new UserAlreadyExistsException ("User already exists");
			
		if ( CollectionUtils.isEmpty(userdto.getRoles()) ) {
			userdto.setRoles(NewUserRequestDTO.JUST_USER);
		} 
		
		try {
			userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
			return userServiceClient.save(userdto);
		} catch (Exception e) {
			throw new InternalServerErrorRuntimeException(e);
		}
	}
	
	
	private boolean userExists(String userId, String email)  {
		try {
			userServiceClient.headOfUserExists(userId, email);
			return true;
		} catch (Exception e) {
			if (e instanceof ErrorFromUnderlyingService) {
				ErrorFromUnderlyingService errorFromUnderlyingService = (ErrorFromUnderlyingService)e;
				if (errorFromUnderlyingService.getResponse().getStatus()!=HttpStatus.NOT_FOUND.ordinal()) {
					
					/* Lets RestExceptionHandler care on that */
					if(e instanceof RuntimeException)
						throw (RuntimeException)e;
					
					/* Let wrap that to silence */
					throw new SilentExceptionWrapper(e);
				} 
				
			}
		}
		return false;
	}
	


}
