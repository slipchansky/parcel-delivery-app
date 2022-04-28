package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;

import com.stas.parceldelivery.commons.constants.UserRoutes;
import com.stas.parceldelivery.commons.contracts.UsersContract;


@FeignClient(name="user-service", path= UserRoutes.ROOT)
public interface UserServiceClient extends UsersContract {
	
	
}
