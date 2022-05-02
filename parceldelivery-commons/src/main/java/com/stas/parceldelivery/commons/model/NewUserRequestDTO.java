package com.stas.parceldelivery.commons.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.stas.parceldelivery.commons.enums.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewUserRequestDTO {
	@ApiModelProperty(value = "User Name")
	@NotBlank(message = "Username should be defined")
	private String username;
	
	@ApiModelProperty(value = "User Emails")
	@NotBlank(message = "Email should be defined")
	@Email(message="Please provide a valid email address")
	private String email;
	
	@ApiModelProperty(value = "User Roles")
	private Set<Role> roles = new HashSet<>();
	
	public static final Set<Role> JUST_USER = new HashSet<>();
	
	@ApiModelProperty(value = "User's Password for Rigisterin User")
	@NotBlank(message = "Password should be defined")
	@Size(min = 6, max=40, message = "Password should be not shorter than 6 and not longer then 40 symbols")
	private String password;
	
	static {
		JUST_USER.add(Role.ROLE_CLIENT);
	};
	
	public NewUserRequestDTO(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = JUST_USER;
		
	}
	

}
