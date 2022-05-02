package com.stas.parceldelivery.commons.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Data
@Builder (builderMethodName = "__builder")
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@ApiModel(value = "Personal User Information")
public class UserDetailsDTO implements Serializable {
	@ApiModelProperty(value = "User's First Name")
	@NotBlank(message = "First Name should be provided")
	private String firstName;
	@ApiModelProperty(value = "User's Last Name")
	
	@NotBlank(message = "Last Name should be provided")
	private String lastName;
	@ApiModelProperty(value = "User's City")
	
	@NotBlank(message = "City should be provided")
	private String city;
	@ApiModelProperty(value = "User's Address")
	
	@NotBlank(message = "Address should be provided")
	private String address;
	
	@NotBlank(message = "Phone should be provided")
	@ApiModelProperty(value = "User's Phone")
	private String phone;
	
	public UserDetailsDTO(UserDetailsDTO d) {
		super();
		from(this).with(d);
	}
}
