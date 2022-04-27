package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class OrderCancelled implements Serializable {
	private String id;

	public OrderCancelled(String id) {
		super();
		this.id = id;
	}
	
	
}
