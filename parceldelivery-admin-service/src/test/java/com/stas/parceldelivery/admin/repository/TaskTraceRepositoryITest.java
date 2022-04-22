package com.stas.parceldelivery.admin.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.CourierStatus;

import static com.stas.parceldelivery.admin.domain.CourierStatus.*;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.domain.DeliveryTaskTrace;
import com.stas.parceldelivery.admin.repository.TaskRepository;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;
import static com.stas.parceldelivery.admin.repository.SampleBeans.*;



@DataJpaTest
@Transactional
@Rollback
public class TaskTraceRepositoryITest {
	
	@Autowired
	TaskRepository taskrep;
	
	@Autowired
	CourierRepository courep;
	
	
	@Autowired
	TaskTraceRepository tracerep;
	
	
	@Test
	public void findAllByTaskIdTest() {
		
		Courier c = courep.save(courier);
		DeliveryTask t1 = taskrep.save(from(task).clone().update(x -> x.setAssignee(c)).get());
		DeliveryTask t2 = taskrep.save(from(task).clone().update(x -> x.setAssignee(c)).get());
		
		DeliveryTaskTrace tr1 = tracerep.save(from(trace).clone().update(x -> x.setAssignee(c)).update(x -> x.setTask(t1)).get());
		DeliveryTaskTrace tr2 = tracerep.save(from(trace).clone().update(x -> x.setAssignee(c)).update(x -> x.setTask(t1)).get());
		DeliveryTaskTrace tr3 = tracerep.save(from(trace).clone().update(x -> x.setAssignee(c)).update(x -> x.setTask(t2)).get());
		
		assertEquals(2, tracerep.findAllByTaskId(t1.getId()).size());
		assertEquals(1, tracerep.findAllByTaskId(t2.getId()).size());
		
		
		
	}
	

}
