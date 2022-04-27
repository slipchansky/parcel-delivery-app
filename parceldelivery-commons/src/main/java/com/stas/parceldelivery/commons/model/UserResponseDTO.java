package com.stas.parceldelivery.commons.model;

import java.util.HashSet;
import java.util.Set;

import com.stas.parceldelivery.commons.enums.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Common User Information")
public class UserResponseDTO {
	@ApiModelProperty(value = "User Name")
	private String username;
	
	@ApiModelProperty(value = "User Emails")
	private String email;
	
	@ApiModelProperty(value = "User Roles")
	private Set<Role> roles = new HashSet<>();
}
