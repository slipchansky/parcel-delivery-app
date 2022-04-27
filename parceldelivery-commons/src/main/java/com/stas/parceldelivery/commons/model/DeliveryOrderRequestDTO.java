package com.stas.parceldelivery.commons.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// keep up to date with
@ApiModel(value = "Request on Delivery")
public class DeliveryOrderRequestDTO {
	@ApiModelProperty(value = "The Addres where the Parcel should be taken")
	private String addressFrom;
	
	@ApiModelProperty(value = "The Addres where the Parcel should be moved to")
	private String addressTo;
	
	@ApiModelProperty(value = "Contact Phone")
	private String clientPhone;
	
	@ApiModelProperty(value = "Contact Email")
	private String clientEmail;
}
