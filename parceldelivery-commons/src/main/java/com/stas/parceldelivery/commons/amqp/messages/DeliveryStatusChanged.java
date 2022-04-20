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
public class DeliveryStatusChanged implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5254477589549139431L;
	
	
	private String deliveryId;
	private DeliveryStatus newStatus;
}
