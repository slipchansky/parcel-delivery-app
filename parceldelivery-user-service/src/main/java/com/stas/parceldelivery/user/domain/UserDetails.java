package com.stas.parceldelivery.user.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.stas.parceldelivery.commons.model.UserDetailsDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@Data
@Builder(builderClassName = "__UserDetailsBuilder", access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetails extends UserDetailsDTO {
	
	@Id
	private String id;
	private Date created;
	private Date modified;
	
	public UserDetails(UserDetails d) {
		super(d);
		this.id = d.id;
	}
	
	public UserDetails(UserDetailsDTO d) {
		super(d);
	}
	

	@PrePersist
    public void prePersist() {
		modified = created = new Date();
        
    }
 
    @PreUpdate
    public void preUpdate() {
        modified = new Date();
    }	
	
	
	public static UserDetailsBuilder builder() {
		return new UserDetailsBuilder();
	}
	

	public static class UserDetailsBuilder extends __UserDetailsBuilder {
		UserDetailsDTOBuilder b = UserDetailsDTO.__builder();
		
		public UserDetailsBuilder address(String address) {
			b.address(address);
			return this;
		}
		

		public UserDetails build() {
			UserDetailsDTO dto = b.build();
			UserDetails my = super.build();
UserDetails result = from(my).with(dto);
			return result;
		}

		public UserDetailsBuilder city(String city) {
			b.city(city);
			return this;
		}

		public UserDetailsBuilder firstName(String firstName) {
			b.firstName(firstName);
			return this;
		}

		public UserDetailsBuilder lastName(String lastName) {
			b.lastName(lastName);
			return this;
		}

		public UserDetailsBuilder phone(String phone) {
			b.phone(phone);
			return this;
		}
		
		public UserDetailsBuilder id(String id) {
			super.id(id);
			return this;
		}
		
	}

}
