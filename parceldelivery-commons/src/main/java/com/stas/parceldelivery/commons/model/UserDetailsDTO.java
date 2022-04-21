package com.stas.parceldelivery.commons.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

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
public class UserDetailsDTO implements Serializable {
	private String firstName;
	private String lastName;
	private String city;
	private String address;
	private String phone;
	
	public UserDetailsDTO(UserDetailsDTO d) {
		super();
		from(this).with(d);
	}
	
	
}
