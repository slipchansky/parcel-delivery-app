package com.stas.parceldelivery.commons.constants;

public class QueueNames {
	public final static String CLIENT_QUEUE = "delivery_client_queue";
	public final static String COURIER_QUEUE = "delivery_courier_queue";
	public final static String ADMIN_QUEUE = "delivery_admin_queue";
	
	public final static String CLIENT_EXCHANGE = "client_exchange";
	public final static String COURIER_EXCHANGE = "courier_exchange";
	public static final String ADMIN_EXCHANGE = "delivery_admin_exchange";
	public final static String SERVICE_EXCHANGE = "service_exchange";
	
	public static final String FROM_CLIENT = "client";
	public static final String FROM_ADMIN = "service.admin";
	public static final String FROM_COURIER = "service.courier";
	public static final String FROM_SERVICE = "service.*";
	
	
	

}
