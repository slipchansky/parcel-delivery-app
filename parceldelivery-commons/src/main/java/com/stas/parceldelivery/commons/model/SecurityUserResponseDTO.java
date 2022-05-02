package com.stas.parceldelivery.commons.model;

import lombok.Data;

@Data
public class SecurityUserResponseDTO extends UserResponseDTO {
	private String password;

}
