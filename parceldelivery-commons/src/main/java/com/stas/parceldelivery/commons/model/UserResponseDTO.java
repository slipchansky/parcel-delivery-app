package com.stas.parceldelivery.commons.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	@NotBlank(message = "Username should be defined")
	private String username;
	
	@ApiModelProperty(value = "User Emails")
	@NotBlank(message = "Email should be defined")
	@Email(message="Please provide a valid email address")
	private String email;
	
	@ApiModelProperty(value = "User Roles")
	private Set<Role> roles = new HashSet<>();
}
