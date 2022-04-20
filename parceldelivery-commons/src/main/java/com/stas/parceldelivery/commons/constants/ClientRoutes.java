package com.stas.parceldelivery.commons.constants;

public class ClientRoutes {
	public final static String P_CLIENTID = "clientId";
	public final static String P_DELIVERYID = "deliveryId";
	public final static String P_STATUS = "status";
	
	
	public  final static String ROOT = "/api/v1";
	public  final static String ALL = ROOT+"/clients/{"+P_CLIENTID+"}/deliveries";
	public  final static String ONE = ALL+"/{"+P_DELIVERYID+"}";
	public  final static String STATUSLESSTHAN = ROOT+"/clients/{"+P_CLIENTID+"}/deliveries/find/statusesthan/{"+P_STATUS+"}";
	public  final static String STATUSIS = ROOT+"/clients/{"+P_CLIENTID+"}/deliveries/find/statusis/{"+P_STATUS+"}";
	
	

}
