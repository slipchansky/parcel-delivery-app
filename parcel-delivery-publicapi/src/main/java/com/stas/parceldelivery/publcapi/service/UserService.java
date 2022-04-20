package com.stas.parceldelivery.publcapi.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.stas.parceldelivery.publcapi.domain.Role;
import com.stas.parceldelivery.publcapi.domain.User;
import com.stas.parceldelivery.publcapi.dto.ResponseBase;
import com.stas.parceldelivery.publcapi.dto.SignUpResponse;
import com.stas.parceldelivery.publcapi.dto.SignupRequest;
import com.stas.parceldelivery.publcapi.exceptions.InternalServerErrorRuntimeException;
import com.stas.parceldelivery.publcapi.exceptions.UserAlreadyExistsException;
import com.stas.parceldelivery.publcapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private SignUpResponse createUser(SignupRequest userdto) throws UserAlreadyExistsException {
		// TODO. introduce bean validation
		
		if (userRepository.existsById(userdto.getUsername())  )
			throw new UserAlreadyExistsException ("User with this name already exists");
		
		if (userRepository.existsByEmail(userdto.getEmail())  )
			throw new UserAlreadyExistsException ("User with this email already exists");
		
		if ( CollectionUtils.isEmpty(userdto.getRoles()) ) {
			userdto.setRoles(User.JUST_USER);
		} 
		
			
		User user = userdto.toDomainUser();
		
		try {
		  userRepository.save(user);
		} catch (Exception e) {
			throw new InternalServerErrorRuntimeException(e);
		}
		
		return SignUpResponse.builder()
				.username(userdto.getUsername())
				.email(userdto.getEmail())
				.build();
	}
	
	private void addUserRoles(SignupRequest u, Role ... moreRoles) {
		if(u.getRoles()==null) {
			u.setRoles(Stream.of(moreRoles).collect(Collectors.toSet()));
		} else {
			Set<Role> roles = new HashSet<>(u.getRoles());
			roles.addAll(Arrays.asList(moreRoles));
			u.setRoles(roles);
		}
	}
	
	private void removeUserRoles(SignupRequest u, Role ... moreRoles) {
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
	public SignUpResponse createAdmin(SignupRequest u) throws UserAlreadyExistsException {
		addUserRoles(u, Role.ROLE_ADMIN);
		return createUser(u);
	}

	
	@Transactional
	public SignUpResponse createClient(SignupRequest u) throws UserAlreadyExistsException {
		addUserRoles(u, Role.ROLE_CLIENT);
		removeUserRoles(u, Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN);
		return createUser(u);
	}


}
