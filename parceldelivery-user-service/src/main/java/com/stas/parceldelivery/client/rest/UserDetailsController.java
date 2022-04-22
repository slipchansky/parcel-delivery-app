package com.stas.parceldelivery.client.rest;

import static com.stas.parceldelivery.commons.constants.UserRoutes.ROOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.client.service.UserDetailsService;
import com.stas.parceldelivery.commons.contracts.UserDetailsContract;
import com.stas.parceldelivery.commons.model.UserDetailsDTO; 

@RestController
@RequestMapping(ROOT)
public class UserDetailsController implements UserDetailsContract {
	
	@Autowired
	UserDetailsService service;
	
	

	@Override
	public UserDetailsDTO update(String userId, UserDetailsDTO d) {
		return service.save(userId, d);
	}
	
	@Override
	public UserDetailsDTO getDetails(String userId) {
		return service.get(userId);
	}

}
