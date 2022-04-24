package com.stas.parceldelivery.user.repository;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.user.domain.UserDetails;
import com.stas.parceldelivery.user.repository.UserDetailsRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.transaction.Transactional;

@DataJpaTest
@Transactional
@Rollback
public class UserDetailsRepositoryITest {
	
	@Autowired
	UserDetailsRepository repository;
	
	@Test
	public void testSaveUser() {
		UserDetails details = UserDetails.builder()
				.id("stas")
				.firstName("stas")
				.lastName("lipchansky")
				.address("home")
				.phone("12345")
				.city("home")
				.build();
		
		UserDetails d = repository.save(details);
		assertNotNull(d.getCreated());
		assertNotNull(d.getModified());
		assertEquals("stas", details.getId());
		assertEquals("stas", details.getFirstName());
		assertEquals("lipchansky", details.getLastName());
		assertEquals("home", details.getAddress());
		assertEquals("12345", details.getPhone());
		assertEquals("home", details.getCity());
	}
	
	@Test
	public void testGetUserById() {
		UserDetails details = UserDetails.builder()
				.id("stas")
				.firstName("stas")
				.lastName("lipchansky")
				.address("home")
				.phone("12345")
				.city("home")
				.build();
		
		repository.save(details);
		Optional<UserDetails> o = repository.findById("stas");
		assertTrue(o.isPresent());
		UserDetails d = o.get();
		assertNotNull(d.getCreated());
		assertNotNull(d.getModified());
		assertEquals("stas", details.getId());
		assertEquals("stas", details.getFirstName());
		assertEquals("lipchansky", details.getLastName());
		assertEquals("home", details.getAddress());
		assertEquals("12345", details.getPhone());
		assertEquals("home", details.getCity());
	}
	

}
