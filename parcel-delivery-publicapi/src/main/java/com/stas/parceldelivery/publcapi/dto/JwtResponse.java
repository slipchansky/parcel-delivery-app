package com.stas.parceldelivery.publcapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse extends ResponseBase {
	
	public static final String TOKEN_TYPE ="Bearer"; 
	
	private String token;
	private String type = TOKEN_TYPE;
	private String username;
	private String email;
	private List<String> roles;
	
	public JwtResponse(String message) {
		super(message);
	}
	
	
	

}
