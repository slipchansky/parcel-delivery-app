package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.UserRoutes;
import com.stas.parceldelivery.commons.contracts.UsersContract;

import feign.FeignException;


@FeignClient(name="user-service", url = "user-service:8000", path= UserRoutes.ROOT)
public interface UserServiceClient extends UsersContract {
	
	default public boolean userExists(String userName, String email) {
		try {
			headOfUserExists(userName, email);
			return true;
		} catch( FeignException.NotFound e) {
			return false;
		} 
	}
	
	
}
