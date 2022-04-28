package com.stas.parceldelivery.publcapi.exceptions;

public class SilentExceptionWrapper extends  RuntimeException {
	
	public SilentExceptionWrapper(Exception cause) {
		super(cause);
	}
	

}
