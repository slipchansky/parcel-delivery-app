package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.CourierRoutes;
import com.stas.parceldelivery.commons.contracts.CourierContract;

@FeignClient(name="courier-service", path=CourierRoutes.ROOT)
public interface CourierServiceClient extends CourierContract {

}
