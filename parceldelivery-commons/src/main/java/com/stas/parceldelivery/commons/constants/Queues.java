package com.stas.parceldelivery.commons.constants;

import java.util.Arrays;
import java.util.List;

public class Queues {
	public final static String AdminOrderCreated = "AdminOrderCreated";
	public final static String AdminLocationChanged = "AdminLocationChanged";
	public final static String AdminOrderCancelled = "AdminOrderCancelled";
	public final static String AdminOrderUpdated = "AdminOrderUpdated";
	public final static String AdminStatusChanged = "AdminStatusChanged";
	public final static String ClientLocationChanged = "ClientLocationChanged";
	public final static String ClientOrderAssigned = "ClientOrderAssigned";
	
	public final static String ClientStatusChanged = "ClientStatusChanged";
	public final static String CourierTaskAssigned = "CourierTaskAssigned";
	public final static String CourierOrderCancelled = "CourierOrderCancelled";
	public final static String CourierOrderUpdated = "CourierOrderUpdated";
	

	
	public static List<String> all() {
		return Arrays.asList(AdminOrderCreated, AdminLocationChanged ,AdminOrderCancelled,AdminOrderUpdated,AdminStatusChanged,ClientLocationChanged,ClientOrderAssigned,ClientStatusChanged,CourierTaskAssigned,CourierOrderCancelled,CourierOrderUpdated);
	}
}
