package com.stas.parceldelivery.commons.model;

import static com.stas.parceldelivery.commons.util.BeanConverter.from;

import java.util.HashSet;
import java.util.Set;

import com.stas.parceldelivery.commons.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends UserResponseDTO {
	public static final Set<Role> JUST_USER = new HashSet<>();
	private String password;
	static {
		JUST_USER.add(Role.ROLE_CLIENT);
	};
	
	public UserDTO(String username, String email, String password) {
		super(username, email, JUST_USER);
		
		this.password = password;
	}
	
	public static class UserDTOBuilder extends UserResponseDTOBuilder {
		private String password;

		@Override
		public UserDTOBuilder email(String email) {
			 super.email(email);
			 return this;
		}

		@Override
		public UserDTOBuilder roles(Set<Role> roles) {
			 super.roles(roles);
			 return this;
		}

		@Override
		public UserDTOBuilder username(String username) {
			 super.username(username);
			 return this;
		}
		
		public UserDTOBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public UserDTO build() {
			UserResponseDTO u = super.build();
			UserDTO result = from(u).to(UserDTO.class);
			result.setPassword(password);
			return result;
		}
	}; 

}
