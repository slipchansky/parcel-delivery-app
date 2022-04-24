package com.stas.parceldelivery.user.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.commons.enums.Role;
import com.stas.parceldelivery.user.domain.User;
import com.stas.parceldelivery.user.repository.UserRepository;

import static com.stas.parceldelivery.commons.enums.Role.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
@Transactional
@Rollback
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	
	
	
	@Test
	public void findAllByByRoleTest() {
repository.save(User.builder().roles(of(ROLE_ADMIN, ROLE_CLIENT)).email("a").username("b").build());
repository.save(User.builder().roles(of (ROLE_ADMIN, ROLE_COURIER)).email("b").username("c").build());
repository.save(User.builder().roles(of(ROLE_ADMIN, ROLE_COURIER)).email("d").username("e").build());
		
		assertEquals(3, repository.findAllByByRole(ROLE_ADMIN).size());
		assertEquals(1, repository.findAllByByRole(ROLE_CLIENT).size());
		assertEquals(2, repository.findAllByByRole(ROLE_COURIER).size());
	}
	
	@Test
	public void existsByUsernameOrEmail_isTrue_Test() {
		repository.save(User.builder().roles(of(ROLE_CLIENT)).username("a").email("b").build());
		assertTrue(repository.existsByUsernameOrEmail("a", "x"));
		assertTrue(repository.existsByUsernameOrEmail("y", "b"));
		assertTrue(repository.existsByUsernameOrEmail("a", "b"));
		
	}
	
	@Test
	public void existsByUsernameOrEmail_isFalse_Test() {
		repository.save(User.builder().roles(of(ROLE_ADMIN, ROLE_CLIENT)).email("a").username("b").build());
		assertFalse(repository.existsByUsernameOrEmail("c", "d"));
	}
	
	private static Set<Role> of(Role ... r) {
		return Stream.of(r).collect(Collectors.toSet());
	}
}
