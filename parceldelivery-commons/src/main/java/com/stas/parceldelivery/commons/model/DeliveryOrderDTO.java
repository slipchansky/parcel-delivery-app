package com.stas.parceldelivery.commons.model;

import java.time.LocalDateTime;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderDTO {
	private String id;
	private String client;
	private String addressFrom;
	private String addressTo;
	private DeliveryStatus status;
	LocalDateTime created;
	LocalDateTime modified;
}
