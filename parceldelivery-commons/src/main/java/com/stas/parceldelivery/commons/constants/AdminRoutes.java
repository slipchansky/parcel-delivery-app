package com.stas.parceldelivery.commons.constants;

public class AdminRoutes {
	public final static String P_COURIER_ID = "courierId";
	public final static String P_TASK_ID = "taskId";
	public final static String P_TASK_STATE = "status";
	
	
	public  final static String ROOT = "/api/v1";
	
	public final static String COURIERS = "/couriers";
	
	public final static String COURIERS_FREE = COURIERS+"/free"; 
	public final static String COURIERS_BUSY = COURIERS+"/busy"; 
	public final static String COURIER_GIVEN = COURIERS+"/{"+P_COURIER_ID+"}";
	
	public final static String TASKS = "/tasks"; 
	public final static String TASK_GIVEN = TASKS+"/{"+P_TASK_ID+"}";
	public static final String TASKS_UNASSIGNED = TASKS+"/unassigned";
	public static final String TASK_ASSIGN = TASK_GIVEN+"/to/{"+P_COURIER_ID+"}";
	
}
