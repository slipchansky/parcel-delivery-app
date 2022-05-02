package com.stas.parceldelivery.user.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import com.stas.parceldelivery.user.domain.UserDetails;
import com.stas.parceldelivery.user.repository.UserDetailsRepository;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	Validator validator;
	
	@Transactional
	// allows partial updates
	public UserDetailsDTO update(String id, UserDetailsDTO delta) {
		UserDetails userDetails;
		Optional<UserDetails> found = userDetailsRepository.findById(id);
		if(found.isPresent()) {
			userDetails = found.get();
		} else {
			userDetails = new UserDetails();
			userDetails.setId(id);
			log.debug("New user details arrived for {}", id);
		}
		userDetails = from(userDetails).with(delta);
		
		// as we allow partial update we should validate details only after partial merge of request to existing details 
		validate(userDetails);
		
		UserDetails result = userDetailsRepository.save(
				userDetails
				);
		
		log.debug("User updated: {}", delta);
		return from(result).to(UserDetailsDTO.class);
	}


	private void validate(UserDetails userDetails) {
		validator.validate(userDetails);
	}


	public UserDetailsDTO get(String userId) {
		Optional<UserDetails> user = userDetailsRepository.findById(userId);
		if(!user.isPresent()) {
			// stas. it's up for discussion
			return new UserDetailsDTO();
		}
		return from(user.get()).to(UserDetailsDTO.class);
	}

}
