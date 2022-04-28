package com.stas.parceldelivery.commons.contracts;

import java.io.InputStream;


import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stas.parceldelivery.commons.exceptions.CommunicationErrorException;
import com.stas.parceldelivery.commons.exceptions.ErrorFromUnderlyingService;
import com.stas.parceldelivery.commons.model.ErrorResponse;

import feign.Response;
import feign.codec.ErrorDecoder;


public class FeignErrorHandler implements ErrorDecoder {
	
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	@Override
	public Exception decode(String methodKey, Response response) {
		HttpStatus httpStatus = HttpStatus.resolve(response.status());
		ErrorResponse fromService; 
		try (InputStream in = response.body().asInputStream()) {
			fromService = OBJECT_MAPPER.readValue(in, ErrorResponse.class);
		} catch(Exception e) {
			throw new CommunicationErrorException("Error "+httpStatus+" on call to "+methodKey);
		}
		return new ErrorFromUnderlyingService(fromService);
	}


}
