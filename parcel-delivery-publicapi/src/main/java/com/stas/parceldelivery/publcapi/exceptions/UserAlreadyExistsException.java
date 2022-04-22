package com.stas.parceldelivery.publcapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
