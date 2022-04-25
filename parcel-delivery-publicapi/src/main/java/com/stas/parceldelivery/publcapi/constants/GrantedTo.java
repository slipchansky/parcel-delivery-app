package com.stas.parceldelivery.publcapi.constants;

public class GrantedTo {
	public final static String CLIENT = "hasRole('CLIENT')";
	public final static String COURIER = "hasRole('COURIER')";
	public final static String ADMIN = "hasRole('ADMIN')";
	public final static String SUPER_ADMIN = "hasRole('SUPER_ADMIN')";
	private final static String or = " or ";
	
	public final static String ANY = CLIENT+or+COURIER+or+ADMIN+or+SUPER_ADMIN;

}
