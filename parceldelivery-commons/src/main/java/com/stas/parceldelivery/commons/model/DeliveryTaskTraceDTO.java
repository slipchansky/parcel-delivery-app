package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryTaskTraceDTO {
	private Date created;
	private CourierDTO assignee;
	private DeliveryStatus status;
	private String location;
}
