package com.stas.parceldelivery.commons.constants;

public class ClientRoutes {
	public final static String P_CLIENTID = "clientId";
	public final static String P_DELIVERYID = "deliveryId";
	public final static String P_STATUS = "status";
	
	
	public  final static String ROOT = "/api/v1/clients";
	public  final static String GIVEN_CLIENT = "/{"+P_CLIENTID+"}";
	public  final static String ALL = "/deliveries";
	public  final static String SINGLE = "/{"+P_DELIVERYID+"}";
	public  final static String STATUSLESSTHAN = "/find/statusesthan/{"+P_STATUS+"}";
	public  final static String STATUSIS = "/find/status/{"+P_STATUS+"}";
}
