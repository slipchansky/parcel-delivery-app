package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.enums.TaskState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Delivery Task information")
public class DeliveryTaskDTO {
	@ApiModelProperty(value = "Tracking Number")
	private String id; // from DeliveryOrder
	
	@ApiModelProperty(value = "Delivery From")
	private String addressFrom;
	
	@ApiModelProperty(value = "Delivery From")
	private String addressTo;
	
	@ApiModelProperty(value = "Delivery To")
	private String clientPhone;
	
	@ApiModelProperty(value = "Contact Email that Client provided for contacts")
	private String clientEmail;
	@ApiModelProperty(value = "Current Location of Parcell")
	private String location;
	
	@ApiModelProperty(value = "Actual Status of Order")
	private DeliveryStatus status;
	
	@ApiModelProperty(value = "The Date when the Order was Created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss Z")
	private Date created;
	
	@ApiModelProperty(value = "The Date when the Order was Modified last time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss Z")
	private Date modified;
	
	@ApiModelProperty(value = "The State of Order From the Amin's point of view")
	private TaskState state;
	
	@ApiModelProperty(value = "The courier who works on Delivering of the Parcel")
	private CourierDTO assignee;

}
