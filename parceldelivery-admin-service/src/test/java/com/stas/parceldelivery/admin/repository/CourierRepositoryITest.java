package com.stas.parceldelivery.admin.repository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.admin.domain.Courier;

import static com.stas.parceldelivery.admin.SampleBeans.*;
import static com.stas.parceldelivery.commons.enums.CourierStatus.*;

import com.stas.parceldelivery.commons.enums.CourierStatus;

import static org.junit.jupiter.api.Assertions.*;


import javax.transaction.Transactional;

@DataJpaTest
@Transactional
@Rollback
public class CourierRepositoryITest {
	
	@Autowired
	CourierRepository repository;
	
	
	@Test
	public void testCreate() {
		Courier c = repository.save(courier);
		assertNotNull(c.getId());
	} 
	
	
	@Test
	public void testFindByStatus() {
		repository.save(courier("c1", free));
		repository.save(courier("c2", free));
		repository.save(courier("c3", busy));
		assertEquals(2, repository.findAllByStatus(CourierStatus.free).size());
		assertEquals(1, repository.findAllByStatus(CourierStatus.busy).size());
	} 
	

}
