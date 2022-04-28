package com.stas.parceldelivery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feign.FeignException;

@Service
public class UserExistsService {
	
	@Autowired
	UserServiceClient users;

	public boolean userExists(String userId, String email) {
		try {
			users.headOfUserExists(userId, email);
		} catch (FeignException.NotFound e) {
			return false;
		}
		return true;
	}
	

}
