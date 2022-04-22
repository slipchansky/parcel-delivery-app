package com.stas.parceldelivery.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.CourierStatus;

import static com.stas.parceldelivery.admin.domain.CourierStatus.*;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import static com.stas.parceldelivery.admin.repository.SampleBeans.*;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
		repository.save(courier(FREE));
		repository.save(courier(FREE));
		repository.save(courier(UNAVAILABLE));
		repository.save(courier(BUSY));
		List<Courier> found = repository.findAllByStatus(FREE);
		
		assertNotNull(found);
		assertEquals(2, found.size());
	} 
	
	@Test
	public void testTaskAssignment() {
		
	}
	
	

}
