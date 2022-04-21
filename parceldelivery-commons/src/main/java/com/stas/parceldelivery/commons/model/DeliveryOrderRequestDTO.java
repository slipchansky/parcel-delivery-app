package com.stas.parceldelivery.commons.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// keep up to date with
public class DeliveryOrderRequestDTO {
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
}
