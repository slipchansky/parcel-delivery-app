package com.stas.parceldelivery.commons.constants;

public class PublicApiRoutes {
	public static final String ROOT = "/api/v1";
	private final static String DELIVERIES = "/deliveries";
	private final static String COURIER = "/courier";
	private final static String CLIENT = "/client";
	private final static String ADMIN = "/admin";
	public static final String USERS = ROOT+"/users";
	
	public static final String CLIENT_DELIVERIES = ROOT+CLIENT+DELIVERIES;
	public static final String COURIER_DELIVERIES = ROOT+COURIER+DELIVERIES;
	public static final String ADMIN_ALL = ROOT+ADMIN+DELIVERIES;
}
