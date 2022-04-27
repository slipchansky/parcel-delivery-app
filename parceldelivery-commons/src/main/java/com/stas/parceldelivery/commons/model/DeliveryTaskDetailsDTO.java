package com.stas.parceldelivery.commons.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Detailed Delivery Order information")
public class DeliveryTaskDetailsDTO extends DeliveryTaskDTO {
	@ApiModelProperty(value = "The Tracking History")
	private List<DeliveryTaskTraceDTO> history;
}
