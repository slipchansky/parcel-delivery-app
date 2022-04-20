package com.stas.parceldelivery.commons.exceptions;

public class AlreadyExistsException extends RuntimeException {

	public AlreadyExistsException() {
		super();
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

}
