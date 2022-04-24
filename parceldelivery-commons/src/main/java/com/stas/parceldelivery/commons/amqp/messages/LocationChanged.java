package com.stas.parceldelivery.commons.amqp.messages;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationChanged  extends OrderModification {
	private String id;
	private String location;
}
