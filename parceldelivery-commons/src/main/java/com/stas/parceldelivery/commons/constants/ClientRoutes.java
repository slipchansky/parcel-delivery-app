package com.stas.parceldelivery.commons.constants;

public class ClientRoutes {
	public final static String P_CLIENTID = "clientId";
	public final static String P_DELIVERYID = "deliveryId";
	public final static String P_STATUS = "status";
	
	
	public  final static String ROOT = "/api/v1/client";
	public  final static String GIVEN_CLIENT = "/{"+P_CLIENTID+"}";
	public  final static String DELIVERIES_ALL = "/deliveries";
	public  final static String DELIVERIES_SINGLE = DELIVERIES_ALL+"/{"+P_DELIVERYID+"}";
	public  final static String DELIVERIES_STATUSLESSTHAN = DELIVERIES_ALL+"/find/statusesthan/{"+P_STATUS+"}";
	public  final static String DELIVERIES_STATUSIS = DELIVERIES_ALL+"/find/status/{"+P_STATUS+"}";
}
