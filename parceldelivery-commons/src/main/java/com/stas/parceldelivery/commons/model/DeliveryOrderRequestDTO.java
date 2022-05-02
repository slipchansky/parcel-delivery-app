package com.stas.parceldelivery.commons.model;

import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "Address From should be provided")
	private String addressFrom;
	
	@ApiModelProperty(value = "The Addres where the Parcel should be moved to")
	@NotBlank(message = "Address To should be provided")
	private String addressTo;
	
	@ApiModelProperty(value = "Contact Phone")
	@NotBlank(message = "Client Phone should be provided")
	private String clientPhone;
	
	@ApiModelProperty(value = "Contact Email")
	@NotBlank(message = "Client Email should be provided")
	private String clientEmail;
}
