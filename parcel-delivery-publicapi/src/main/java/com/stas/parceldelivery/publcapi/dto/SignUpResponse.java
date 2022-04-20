package com.stas.parceldelivery.publcapi.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stas.parceldelivery.publcapi.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@Builder
public class SignUpResponse extends ResponseBase {
	private String username;
	private String email;
	private Set<Role> roles;
	
	public SignUpResponse(String message) {
		super(message);
	}
	
}
