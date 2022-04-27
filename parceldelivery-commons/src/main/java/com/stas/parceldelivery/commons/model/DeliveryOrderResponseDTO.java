package com.stas.parceldelivery.commons.model;

import java.util.Date;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;

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
@ApiModel(value = "Delivery Order information")
public class DeliveryOrderResponseDTO {

	@ApiModelProperty(value = "The Tracking Number")
	private String id;

	@ApiModelProperty(value = "Client Identifier")
	private String client;

	@ApiModelProperty(value = "The Addres where the Parcel should be taken")
	private String addressFrom;
	@ApiModelProperty(value = "The Addres where the Parcel should be moved to")
	private String addressTo;
	
	@ApiModelProperty(value = "Contact Phone")
	private String clientPhone;
	
	@ApiModelProperty(value = "Contact Email")
	private String clientEmail;
	
	@ApiModelProperty(value = "Actual Location of the Parcel")
	private String location;
	
	@ApiModelProperty(value = "Actual Delivery Status")
	private DeliveryStatus status;
	
	@ApiModelProperty(value = "The Date and Time of Delivery Order creation")
	Date created;
	
	@ApiModelProperty(value = "The Date and Time of last Modifivation of Delivery Order")
	Date modified;
}
