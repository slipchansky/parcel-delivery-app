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
public class CourierTaskRepositoryITest {
	
	// FIXME. stas. should be implemented		
	

}
