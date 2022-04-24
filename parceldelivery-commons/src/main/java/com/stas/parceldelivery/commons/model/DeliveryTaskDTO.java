package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.enums.TaskState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryTaskDTO {
	private String id; // from DeliveryOrder
	
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
	private String location;
	private Date created;
	private Date modified;
	private DeliveryStatus status;
	private TaskState state;
	private CourierDTO assignee;

}
