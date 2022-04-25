package com.stas.parceldelivery.commons.constants;

public class PublicApiRoutes {
	public static final String ROOT = "/api/v1";
	private final static String DELIVERIES = "/deliveries";
	private final static String COURIER = "/courier";
	
	public final static String CLIENT = "/client";
	public final static String ADMIN = ROOT+"/admin";
	public static final String USERS = ROOT+"/users";
	
	public static final String CLIENT_DELIVERIES = CLIENT+DELIVERIES;
	public static final String COURIER_DELIVERIES = COURIER+DELIVERIES;
	public static final String ADMIN_ALL = ADMIN+DELIVERIES;
}
