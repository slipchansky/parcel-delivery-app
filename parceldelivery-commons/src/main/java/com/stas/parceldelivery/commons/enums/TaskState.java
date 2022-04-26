package com.stas.parceldelivery.commons.enums;

import java.util.HashMap;
import java.util.Map;

import com.stas.parceldelivery.commons.exceptions.BadRequestException;

public enum TaskState {
	NEW, PROGRESS, FINISHED, CANCELLED;
	
	private final static Map<DeliveryStatus, TaskState> stateMapping = new HashMap<>();
	static {
		stateMapping.put(DeliveryStatus.ASSIGNED, PROGRESS);
		stateMapping.put(DeliveryStatus.CANCELED, CANCELLED);
		stateMapping.put(DeliveryStatus.INPROGRESS, PROGRESS);
		stateMapping.put(DeliveryStatus.FINISHED, FINISHED);
		stateMapping.put(DeliveryStatus.CREATED, NEW);
	}
	
	public static TaskState fromDeliveryStatus(DeliveryStatus status) {
		if(!stateMapping.containsKey(status)) {
			throw new BadRequestException("Unknown delivery status "+status);
		}
		return stateMapping.get(status); 
	}
	
	
}
