package com.stas.parceldelivery.commons.amqp.messages;

import java.time.LocalDateTime;
import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.Data;

@Data
public class ParcelDeliveryMessage {
	
	private String id;
	private String client;
	private String address;
	private DeliveryStatus status;
	Date created;

}
