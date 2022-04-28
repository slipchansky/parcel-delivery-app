package com.stas.parceldelivery.commons.exceptions;

import com.stas.parceldelivery.commons.model.ErrorResponse;


public class ErrorFromUnderlyingService extends Exception {
	private ErrorResponse response;
	public ErrorFromUnderlyingService(ErrorResponse response) {
		this.response = response;
	}
	
	public ErrorResponse getResponse() {
		return response;
	}
	

}
