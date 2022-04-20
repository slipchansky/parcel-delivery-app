package com.stas.parceldelivery.client.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(builderClassName = "__UserDetailsBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetails extends UserDetailsDTO {
	@Id
	private String id;
	LocalDateTime created;
	LocalDateTime modified;
	
	public UserDetails(UserDetails d) {
		super(d);
		this.id = d.id;
	}
	
//	private UserDetails merge(UserDetailsDTO d) {
//		super.merge(d);
//		if(d.id != null) this.id = d.id;
//		return this;
//	}
	
	public UserDetails(UserDetailsDTO d) {
		super(d);
	}
	

	@PrePersist
    public void prePersist() {
		modified = created = LocalDateTime.now();
        
    }
 
    @PreUpdate
    public void preUpdate() {
        modified = LocalDateTime.now();
    }	
	
	
	public static __UDB builder() {
		return new __UDB();
	}
	
	
	
	public static class __UDB extends __UserDetailsBuilder {
		UserDetailsDTOBuilder b = UserDetailsDTO.__builder();

		
		public __UDB address(String address) {
			b.address(address);
			return this;
		}
		

		public UserDetails build() {
			UserDetailsDTO dto = b.obuild();
			UserDetails my = super.build();
			my.merge(dto);
			return my;
		}

		public __UDB city(String city) {
			b.city(city);
			return this;
		}

		public __UDB firstName(String firstName) {
			b.firstName(firstName);
			return this;
		}

		public __UDB lastName(String lastName) {
			b.lastName(lastName);
			return this;
		}

		public __UDB phone(String phone) {
			b.phone(phone);
			return this;
		}
		
		public __UDB id(String id) {
			super.id(id);
			return this;
		}
		
		
	}
	
	

}
