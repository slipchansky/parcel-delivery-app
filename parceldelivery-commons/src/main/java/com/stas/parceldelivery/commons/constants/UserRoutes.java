package com.stas.parceldelivery.commons.constants;

public class UserRoutes {
	public final static String P_USER_ID = "userId";
	public static final String P_ROLE = "role"; 
	public static final String ROOT = "/api/v1/users";
	public  final static String USER_DETAILS = "/{"+P_USER_ID+"}/details";
	public  final static String USER = "/{"+P_USER_ID+"}";
	public  final static String USERS = "/";
	public  final static String BYROLE = "/{"+P_ROLE+"}";
}
