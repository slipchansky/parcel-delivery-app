package com.stas.parceldelivery.commons.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

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
	private String firstName;
	@ApiModelProperty(value = "User's Last Name")
	private String lastName;
	@ApiModelProperty(value = "User's City")
	private String city;
	@ApiModelProperty(value = "User's Address")
	private String address;
	@ApiModelProperty(value = "User's Phone")
	private String phone;
	
	public UserDetailsDTO(UserDetailsDTO d) {
		super();
		from(this).with(d);
	}
	
	
}
