package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdated  extends OrderModification {
	private String id;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
}
