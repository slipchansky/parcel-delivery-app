package com.stas.parceldelivery.admin.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

@Entity 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String username;
	private String firstName;
	private String lastName;
	private String city;
	private String email;
	private String address;
	private String phone;
	@Enumerated(EnumType.STRING)
	private CourierStatus status;
}
