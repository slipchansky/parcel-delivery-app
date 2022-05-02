package com.stas.parceldelivery.commons.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDestinationRequest {
	@ApiModelProperty(value = "The Addres where the Parcel should be moved to")
	@NotBlank(message = "Address To should be provided")
	private String addressTo;
}
