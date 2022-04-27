package com.stas.parceldelivery.commons.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Courier's Task")
public class CourierTaskDTO {
	@ApiModelProperty(value = "Task Tracking Number")
	private String id;
	
//	@ApiModelProperty(value = "Courier Identifier")
//	private String courierId;
//	
//	@ApiModelProperty(value = "Client Identifier")
//	private String client;

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
	private Date created;
	
	@ApiModelProperty(value = "The Date when the Order was Modified last time")
	private Date modified;

}
