package com.stas.parceldelivery.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;

import com.stas.parceldelivery.commons.constants.UserRoutes;
import com.stas.parceldelivery.commons.contracts.UsersContract;

@FeignClient(name="user-service", url = "user-service:8000", path= UserRoutes.ROOT, decode404 = true)
public interface UserServiceClient extends UsersContract {
	
	default public boolean userExists(String userName, String email) {
		return headOfUserExists(userName, email).getStatusCode()==HttpStatus.NO_CONTENT;
}
	
}
