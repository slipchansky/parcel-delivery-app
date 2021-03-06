package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.AdminRoutes;
import com.stas.parceldelivery.commons.contracts.AdminContract;
import com.stas.parceldelivery.commons.contracts.ClientContract;

@FeignClient(name="admin-service", path=AdminRoutes.ROOT)
public interface AdminServiceClient extends AdminContract {
}
