package com.stas.parceldelivery.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.UpdateDestinationRequest;
import com.stas.parceldelivery.user.domain.UserDetails;
import com.stas.parceldelivery.user.repository.UserDetailsRepository;
import com.stas.parceldelivery.user.service.UserDetailsService;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserDetailsService.class)
public class UserDetailsServiceTest {
	
	// FIXME. stas. add tests!!!
	@MockBean
	UserDetailsRepository userRepository;
	
}
