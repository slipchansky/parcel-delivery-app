package com.stas.parceldelivery.courier.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.commons.enums.DeliveryStatus;
import com.stas.parceldelivery.courier.domain.CourierTask;
import com.stas.parceldelivery.courier.repository.CourierTaskRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class TaskRepositoryITest {
	
//	@Autowired
//	TaskRepository repository;
//	
//	@Test
//	public void testCreateDelivery() {
//		CourierDeliveryTask d = CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.CREATED)
//				.addressFrom("home").build();
//		
//		d = repository.save(d);
//		
//		assertNotNull(d.getId());
//		assertNotNull(d.getCreated());
//		assertNotNull(d.getModified());
//		assertEquals("test", d.getClient());
//		assertEquals(DeliveryStatus.CREATED, d.getStatus());
//		assertEquals("home", d.getAddressFrom());
//	}
//	
//	@Test
//	public void testFindAllByClientAndStatusLessThan() {
//		
//		repository.save(CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.CREATED)
//				.addressFrom("home").build());
//		
//		repository.save(CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.INRETURN)
//				.addressFrom("home").build());
//		
//		List<CourierDeliveryTask> found = repository.findAllByClientAndStatusLessThan("test", DeliveryStatus.INRETURN);
//		assertEquals(1, found.size());
//		found = repository.findAllByClientAndStatusLessThan("test", DeliveryStatus.FINISHED);
//		assertEquals(2, found.size());
//		
//	}
//	
//	
//	@Test
//	public void testFindAllByClientAndStatus() {
//		
//		repository.save(CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.CREATED)
//				.addressFrom("home").build());
//		
//		repository.save(CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.INRETURN)
//				.addressFrom("home").build());
//		
//		List<CourierDeliveryTask> found = repository.findAllByClientAndStatus("test", DeliveryStatus.CREATED);
//		assertEquals(1, found.size());
//		
//		found = repository.findAllByClientAndStatus("test", DeliveryStatus.INRETURN);
//		assertEquals(1, found.size());
//		
//	}
//	
//	@Test
//	public void testFindByIdAndClient() {
//		CourierDeliveryTask result = repository.save(CourierDeliveryTask.builder()
//				.client("test")
//				.status(DeliveryStatus.CREATED)
//				.addressFrom("home").build());
//		
//		assertTrue(repository.findByIdAndClient(result.getId(), "test").isPresent()); 
//	}
	
	

}
