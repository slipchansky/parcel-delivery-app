package com.stas.parceldelivery.publcapi.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.publcapi.dto.ResponseBase;
import com.stas.parceldelivery.publcapi.exceptions.InternalServerErrorRuntimeException;
import com.stas.parceldelivery.publcapi.exceptions.UserAlreadyExistsException;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Service
public class UserService {
	
	@Autowired
	UserServiceClient userServiceClient;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private UserResponseDTO createUser(UserDTO userdto) throws UserAlreadyExistsException {
		// TODO. introduce bean validation
		
		if(userServiceClient.userExists( userdto.getUsername(), userdto.getEmail()))
			throw new UserAlreadyExistsException ("User already exists");
			
		
		
		if ( CollectionUtils.isEmpty(userdto.getRoles()) ) {
			userdto.setRoles(UserDTO.JUST_USER);
		} 
		
		try {
			userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
			return userServiceClient.save(userdto);
		} catch (Exception e) {
			throw new InternalServerErrorRuntimeException(e);
		}
	}
	
	
	@Transactional
	public UserResponseDTO createClient(UserDTO u) throws UserAlreadyExistsException {
		Set<Role> roles = u.getRoles();
		roles.remove(Role.ROLE_SUPER_ADMIN);
		return createUser(u);
	}


}
