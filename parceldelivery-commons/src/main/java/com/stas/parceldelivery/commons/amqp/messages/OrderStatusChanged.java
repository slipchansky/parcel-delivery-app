package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusChanged extends OrderModification {
	private String id;
	private DeliveryStatus status;
	public OrderStatusChanged(String id) {
		super();
		this.id = id;
	}
}
