package com.stas.parceldelivery.commons.constants;

public class CourierRoutes {
	
	public static final String COURIER_ID = "courierId";
	public final static String TASK_ID = "taskId";
	public final static String P_LOCATION = "location";
	
	
	public  final static String ROOT = "/api/v1/courier";
	public  final static String COURIER_GIVEN = "/{"+COURIER_ID+"}";
	public  final static String TASKS = "/tasks";
	public  final static String TASKS_GIVEN = TASKS+"/{"+TASK_ID+"}";
}
