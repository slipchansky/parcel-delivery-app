package com.stas.parceldelivery.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.admin.domain.Courier;

import static com.stas.parceldelivery.admin.SampleBeans.*;

import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.commons.enums.CourierStatus;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.stas.parceldelivery.commons.enums.CourierStatus.*;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

@DataJpaTest
@Transactional
@Rollback
public class TaskRepositoryITest {
	
	@Autowired
	TaskRepository repository;
	

	
	@Autowired
	public CourierRepository courep;
	@Autowired
	public TaskTraceRepository tracerep;
	
	
	
	@Test
	public void testCreate() {
		DeliveryTask c = repository.save(copy(task));
		assertNotNull(c.getId());
	} 
	
	
	@Test
	public void testAddAsseegnee() {
		Courier c = courep.save(from(courier).clone().get());
		DeliveryTask t = copy(task);
		t.setAssignee(c);
		DeliveryTask saved = repository.save(t);
		Optional<DeliveryTask> result = repository.findById(saved.getId());
		assertEquals(c, result.get().getAssignee());
	}
	
	@Test
	public void testFindTasksByAsseneeId() {
		
		Courier c = courep.save(copy(courier));
		DeliveryTask t = from(task).clone().update(x -> x.setAssignee(c)).get();
		
		repository.save(t);
		DeliveryTask saved = repository.save(t);
		
		List<DeliveryTask> found = repository.findAllByAssigneeId(c.getId());
		assertEquals(1, found.size());
		assertEquals(saved, found.get(0));
		
	}
	
	@Test
	public void testFindTasksByStatus() throws InterruptedException {
		DeliveryTask a = repository.save(copy(task));
		DeliveryTask b = repository.save(copy(task));
		Iterable<DeliveryTask> ddd = repository.findAll();
		
		DeliveryTask expected = repository.save(
				from(task)
				.clone()
				.update(x -> x.setStatus(DeliveryStatus.INPROGRESS))
				.get());
		
		List<DeliveryTask> found = repository.findAllByStatus(DeliveryStatus.INPROGRESS);
		assertEquals(1, found.size());
		assertEquals(expected, found.get(0));
		assertEquals(2, repository.findAllByStatus(DeliveryStatus.CREATED).size());
	}
	
	@Test
	public void testCountTasksByAsseneeId() {
		
		Courier c1 = courep.save(courier("c1", CourierStatus.busy));
		Courier c2 = courep.save(courier("c2", CourierStatus.busy));
		
		
		repository.save(from(task).clone().update(x -> x.setAssignee(c1)).get());
		repository.save(from(task).clone().update(x -> x.setAssignee(c1)).get());
		repository.save(from(task).clone().update(x -> x.setAssignee(c2)).get());
		
		assertEquals(2l, repository.countByAssigneeId(c1.getId()));
		assertEquals(1l, repository.countByAssigneeId(c2.getId()));
		
		
	}
	
}
