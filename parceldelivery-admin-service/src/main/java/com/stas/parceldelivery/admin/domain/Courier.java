package com.stas.parceldelivery.admin.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;

import com.stas.parceldelivery.commons.enums.CourierStatus;

import lombok.*;

@Entity 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {
	@Id
	private String id;
	
	private String firstName;
	private String lastName;
	private String city;
	private String email;
	private String address;
	private String phone;
	@Enumerated(EnumType.STRING)
	private CourierStatus status;
	
	@PrePersist
    public void prePersist() {
		if(id==null) {
			// for simplifying test cases
			id = UUID.randomUUID().toString();
		}
    }
	
}
