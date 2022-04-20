package com.stas.parceldelivery.commons.constants;

public class ClientRoutes {
	public final static String P_CLIENTID = "clientId";
	public final static String P_DELIVERYID = "deliveryId";
	public final static String P_STATUS = "status";
	
	
	public  final static String ROOT = "/api/v1/clients";
	public  final static String ALL = "/{"+P_CLIENTID+"}"+ "/deliveries";
	public  final static String SINGLE = ALL+"/{"+P_DELIVERYID+"}";
	public  final static String STATUSLESSTHAN = ALL+"/find/statusesthan/{"+P_STATUS+"}";
	public  final static String STATUSIS = ALL+"/find/status/{"+P_STATUS+"}";
}
