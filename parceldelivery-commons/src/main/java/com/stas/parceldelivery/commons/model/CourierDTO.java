package com.stas.parceldelivery.commons.model;


import com.stas.parceldelivery.commons.enums.CourierStatus;

import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Courier's Personal Information")
public class CourierDTO {
	@ApiModelProperty(value = "Courier Identifier")
	private String id;
	@ApiModelProperty(value = "Courier First Name")
	private String firstName;
	@ApiModelProperty(value = "Courier Last Name")
	private String lastName;
	@ApiModelProperty(value = "Courier City")
	private String city;
	@ApiModelProperty(value = "Courier Email")
	private String email;
	@ApiModelProperty(value = "Courier Address")
	private String address;
	@ApiModelProperty(value = "Courier Phone Number")
	private String phone;
	
	// TODO. stas. I've forgot to change status on finising task
	@ApiModelProperty(value = "Courier Statue")
	private CourierStatus status;

}
