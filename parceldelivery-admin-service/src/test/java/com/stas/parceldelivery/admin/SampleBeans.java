package com.stas.parceldelivery.admin;

import static com.stas.parceldelivery.commons.enums.CourierStatus.free;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.domain.DeliveryTaskTrace;
import com.stas.parceldelivery.commons.enums.CourierStatus;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

public class SampleBeans {
	
	public final static String uname = "uname";
	public final static Courier courier = courier(free);
	

	public static Courier courier(CourierStatus status) {
		return Courier.builder()
				.id(uname)
				.address("a")
				.firstName("b")
				.lastName("c")
				.email("test@test.com")
				.phone("12345")
				.status(status)
				.build();
	}
	
	public static Courier courier(String uname, CourierStatus status) {
		return Courier.builder()
				.id(uname)
				.address("a")
				.firstName("b")
				.lastName("c")
				.email("test@test.com")
				.phone("12345")
				.status(status)
				.build();
	}
	
	
	public final static DeliveryTask task = DeliveryTask.builder()
			.addressFrom("a")
			.addressTo("b")
			.clientEmail("test@test.com")
			.status(DeliveryStatus.CREATED)
			.build();
	
	public final static DeliveryTaskTrace trace = DeliveryTaskTrace.builder()
			.status(DeliveryStatus.ASSIGNED)
			.location("home")
			.build();
			
	
	
	
	

}
