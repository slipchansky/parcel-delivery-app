package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderCancelled  extends OrderModification {
	private String id;
}
