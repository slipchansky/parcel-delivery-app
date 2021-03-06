package com.stas.parceldelivery.user.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.stas.parceldelivery.commons.enums.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	public static final Set<Role> JUST_USER = new HashSet<>();
	static {
		JUST_USER.add(Role.ROLE_CLIENT);
	};
	
	@Id
	private String username;
	private String email;
	private String password;
	
	@ElementCollection
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name="username"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = new HashSet<>();
	
	public User(String username, String email, String password) {
		this(username, email, password, JUST_USER);
	}
	
	public static UserBuilder builder() {
		return new UserBuilder();
	}
}
