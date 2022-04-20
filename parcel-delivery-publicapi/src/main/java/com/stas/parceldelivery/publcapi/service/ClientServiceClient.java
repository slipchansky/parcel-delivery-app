package com.stas.parceldelivery.publcapi.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.stas.parceldelivery.commons.constants.ClientRoutes;
import com.stas.parceldelivery.commons.contracts.ClientContract;

@FeignClient(name="client-service", url = "client-service:8001", path=ClientRoutes.ROOT)
public interface ClientServiceClient extends ClientContract {
}
