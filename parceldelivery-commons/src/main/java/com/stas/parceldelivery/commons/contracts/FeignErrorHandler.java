package com.stas.parceldelivery.commons.contracts;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

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
		if (httpStatus == HttpStatus.SERVICE_UNAVAILABLE) {
			String errorMessage = getResponseBodyAsString(response);
			fromService = ErrorResponse.builder().status(httpStatus).message(errorMessage).build();
		} else {
				try {
					
					String bodyContent = getResponseBodyAsString(response);
					fromService = OBJECT_MAPPER.readValue(bodyContent, ErrorResponse.class);
					
				} catch(Exception e) {
					fromService = ErrorResponse.builder().status(httpStatus).message("No details").build();
				}
		}
		return new ErrorFromUnderlyingService(fromService);
	}
	
	
	private String getResponseBodyAsString(Response response) {
		String stringContent= "No message";
		if(response.body() != null)
			try (InputStream ins = response.body().asInputStream()) {
				stringContent = StreamUtils.copyToString(ins, Charset.defaultCharset());
			} catch(Exception e) {
			}
		return stringContent;
	}


}
