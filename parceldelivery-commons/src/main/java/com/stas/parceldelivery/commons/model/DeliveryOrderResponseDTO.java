package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryOrderResponseDTO {
	private String id;
	private String client;
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
	private DeliveryStatus status;
	Date created;
	Date modified;
}
