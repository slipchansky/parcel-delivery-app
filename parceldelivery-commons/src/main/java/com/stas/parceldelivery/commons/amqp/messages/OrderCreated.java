package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreated implements Serializable {
	
	private String id;
	private String client;
	private String addressFrom;
	private String addressTo;
	private Date created;
	private String clientPhone;
	private String clientEmail;

}
