package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Delivery Tracking Record")
public class DeliveryTaskTraceDTO {
	@ApiModelProperty(value = "When the record is Created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss Z")
	private Date created;
	
	@ApiModelProperty(value = "Who performed given act")
	private CourierDTO assignee;
	
	@ApiModelProperty(value = "The Status of Delivery at the stage")
	private DeliveryStatus status;
	
	@ApiModelProperty(value = "The Location of Parcel")
	private String location;
}
