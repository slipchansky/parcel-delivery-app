package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierAssignedTask implements Serializable {
	private String id;
	private String courierId;
	private String client;
	private String addressFrom;
	private String addressTo;
	private String clientPhone;
	private String clientEmail;
}
