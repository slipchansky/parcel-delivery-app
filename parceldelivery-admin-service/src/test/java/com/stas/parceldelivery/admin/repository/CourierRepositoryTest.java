package com.stas.parceldelivery.admin.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.CourierStatus;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import static com.stas.parceldelivery.admin.SampleBeans.*;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import java.util.Arrays;
import java.util.Optional;

@DataJpaTest
@Transactional
@Rollback
public class CourierRepositoryTest {
	
	@Autowired
	TaskRepository taskrep;
	
	@Autowired
	CourierRepository repository;
	
	
	
	
	@Test
	public void testAssignment() {
		
		Courier copy = copy(courier);
		Courier saved = repository.save(copy);
		
		DeliveryTask task1 = taskrep.save(from(task).clone().update(x -> x.setAssignee(saved)).get());
		DeliveryTask task2 = taskrep.save(copy(task));
		
		Optional<Courier> found = repository.findById(saved.getId());
		
		int k = 0;
		k++;
		//Courier x = repository.get()
		
	}
	

}
