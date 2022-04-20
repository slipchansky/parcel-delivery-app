package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.UserRoutes;
import com.stas.parceldelivery.commons.contracts.UserDetailsContract;


@FeignClient(name="user-service", url = "user-service:8000", path= UserRoutes.ROOT)
public interface UserDetailsServiceClient extends UserDetailsContract {
}
