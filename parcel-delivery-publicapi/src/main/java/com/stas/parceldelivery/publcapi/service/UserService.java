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
	
	private UserResponseDTO createUser(UserDTO userdto) throws UserAlreadyExistsException {
		// TODO. introduce bean validation
		ResponseEntity<?> result = userServiceClient.exists( userdto );
		if(result.getStatusCode()!=HttpStatus.NO_CONTENT)
			throw new UserAlreadyExistsException ("User already exists");
			
		
		
		if ( CollectionUtils.isEmpty(userdto.getRoles()) ) {
			userdto.setRoles(UserDTO.JUST_USER);
		} 
		
		try {
			return userServiceClient.save(userdto);
		} catch (Exception e) {
			throw new InternalServerErrorRuntimeException(e);
		}
	}
	
	private void addUserRoles(UserDTO u, Role ... moreRoles) {
		if(u.getRoles()==null) {
			u.setRoles(Stream.of(moreRoles).collect(Collectors.toSet()));
		} else {
			Set<Role> roles = new HashSet<>(u.getRoles());
			roles.addAll(Arrays.asList(moreRoles));
			u.setRoles(roles);
		}
	}
	
	private void removeUserRoles(UserDTO u, Role ... moreRoles) {
		if(u.getRoles()==null) {
			u.setRoles(Stream.of(moreRoles).collect(Collectors.toSet()));
		} else {
			Set<Role> roles = new HashSet<>(u.getRoles());
			roles.addAll(Arrays.asList(moreRoles));
			u.setRoles(roles);
		}
	}
	
	
	@Transactional
	@Secured("ROLE_SUPER_ADMIN")
	public UserResponseDTO createAdmin(UserDTO u){
		addUserRoles(u, Role.ROLE_ADMIN);
		return createUser(u);
	}

	
	@Transactional
	public UserResponseDTO createClient(UserDTO u) throws UserAlreadyExistsException {
		addUserRoles(u, Role.ROLE_CLIENT);
		removeUserRoles(u, Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN);
		return createUser(u);
	}


}
