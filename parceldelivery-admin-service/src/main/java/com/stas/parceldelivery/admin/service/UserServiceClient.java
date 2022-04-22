package com.stas.parceldelivery.admin.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.UserRoutes;
import com.stas.parceldelivery.commons.contracts.UsersContract;

@FeignClient(name="user-service", url = "user-service:8000", path= UserRoutes.ROOT)
public interface UserServiceClient extends UsersContract {
}
