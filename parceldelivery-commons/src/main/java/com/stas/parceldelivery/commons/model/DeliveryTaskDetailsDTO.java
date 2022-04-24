package com.stas.parceldelivery.commons.model;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTaskDetailsDTO extends DeliveryTaskDTO {
	private List<DeliveryTaskTraceDTO> history;
}
