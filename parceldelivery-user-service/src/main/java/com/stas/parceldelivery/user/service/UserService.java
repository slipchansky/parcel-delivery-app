package com.stas.parceldelivery.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.commons.exceptions.AlreadyExistsException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserResponseDTO;
import com.stas.parceldelivery.user.domain.User;
import com.stas.parceldelivery.user.repository.UserRepository;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;


@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public UserDTO findById(String userId) {
		Optional<User> found = repository.findById(userId);
		if(!found.isPresent()) {
			throw new NotFoundException(); 
		}
		return from(found.get()).to(UserDTO.class);
	}
	
	public UserResponseDTO save(UserDTO dto) {
		Optional<User> found = repository.findById(dto.getUsername());
		User user;
		if(found.isPresent()) {
			user = from(found.get()).with(dto);
		} else {
			if (repository.existsByEmail(dto.getEmail())) {
				throw new AlreadyExistsException("User with such email already exists");
			}
			
			user = from(dto).to(User.class);
		}
		user = repository.save(user);
		return from(user).to(UserResponseDTO.class);
	}
	
	public boolean exists(String username, String email) {
		return repository.existsByUsernameOrEmail(username, email);
	}
	
	
	public List<UserResponseDTO> listByRole(Role role) {
		return repository
				.findAllByByRole(role).stream()
				.map(u -> from(u).to(UserResponseDTO.class))
				.collect(Collectors.toList());
	}

}
