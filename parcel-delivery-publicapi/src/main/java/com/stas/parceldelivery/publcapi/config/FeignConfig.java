package com.stas.parceldelivery.publcapi.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignConfig {

	public class FeignRequestInterceptor implements RequestInterceptor {
		@Override
		public void apply(RequestTemplate template) {
			template.header(ParceldeliveryHeaders.REQUEST_ID, UUID.randomUUID().toString());
			template.header(ParceldeliveryHeaders.CORRELATION_ID, CallContext.get().getCorrelationId());
		}
	}

	@Bean
	public FeignRequestInterceptor addRequestIdHeaderInterceptor() {
		return new FeignRequestInterceptor();
	}
	
}
