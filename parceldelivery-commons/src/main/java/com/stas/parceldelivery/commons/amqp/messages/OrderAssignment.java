package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAssignment extends OrderModification {
	private String id;
	private String assignee;
}
