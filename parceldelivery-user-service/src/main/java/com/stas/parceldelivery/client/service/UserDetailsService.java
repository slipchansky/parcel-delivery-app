package com.stas.parceldelivery.client.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.client.domain.UserDetails;
import com.stas.parceldelivery.client.repository.UserDetailsRepository;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Transactional
	public UserDetailsDTO save(String id, UserDetailsDTO d) {
		UserDetails userDetails;
		Optional<UserDetails> found = userDetailsRepository.findById(id);
		if(found.isPresent()) {
			userDetails = found.get();
		} else {
			userDetails = new UserDetails();
			userDetails.setId(id);
			log.debug("New user details arrived for {}", id);
		}
		userDetails = from(userDetails).with(d);
		validate(userDetails);
		
		UserDetails result = userDetailsRepository.save(
				userDetails
				);
		
		log.debug("User updated: {}", d);
		return from(result).to(UserDetailsDTO.class);
	}


	private void validate(UserDetails userDetails) {
		// TODO stas. implement that
		
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
