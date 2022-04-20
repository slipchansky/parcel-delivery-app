package com.stas.parceldelivery.commons.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder( builderMethodName = "__builder", buildMethodName = "obuild")
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class UserDetailsDTO implements Serializable {
	private String firstName;
	private String lastName;
	private String city;
	private String address;
	private String phone;
	
	public UserDetailsDTO(UserDetailsDTO d) {
		this.firstName = d.firstName;
		this.lastName = d.lastName;
		this.city = d.city;
		this.address = d.address;
		this.phone = d.phone;
	}
	
	public UserDetailsDTO merge(UserDetailsDTO d) {
		if(d.firstName!=null) this.firstName = d.firstName;
		if(d.lastName!=null) this.lastName = d.lastName;
		if(d.city!=null) this.city = d.city;
		if(d.address!=null) this.address = d.address;
		if(d.phone!=null) this.phone = d.phone;
		return this;
		
	}
	
}
