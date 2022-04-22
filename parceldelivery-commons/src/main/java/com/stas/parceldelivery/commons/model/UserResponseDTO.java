package com.stas.parceldelivery.commons.model;

import java.util.HashSet;
import java.util.Set;

import com.stas.parceldelivery.commons.enums.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
	private String username;
	private String email;
	private Set<Role> roles = new HashSet<>();
}
