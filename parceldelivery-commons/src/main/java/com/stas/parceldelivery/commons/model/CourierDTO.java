package com.stas.parceldelivery.commons.model;


import com.stas.parceldelivery.commons.enums.CourierStatus;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierDTO {
	private String id;
	private String username;
	private String firstName;
	private String lastName;
	private String city;
	private String email;
	private String address;
	private String phone;
	private CourierStatus status;

}
