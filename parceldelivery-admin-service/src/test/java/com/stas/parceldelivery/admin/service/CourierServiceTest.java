package com.stas.parceldelivery.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.repository.CourierRepository;
import com.stas.parceldelivery.commons.enums.CourierStatus;
import com.stas.parceldelivery.commons.model.CourierDTO;
import com.stas.parceldelivery.commons.model.UserDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;



@SpringBootTest(classes = CourierService.class)
public class CourierServiceTest {
	
	@Autowired
	CourierService service;
	
	
	@MockBean
	UserServiceClient userService;
	
	@MockBean
	CourierRepository repository;
	
	private final static String uaddress = "home";
	private final static String firstName = "stas";
	private final static String lastName = "lipchansky";
	private final static String phone = "12345";
	private final static String city = "Donetsk";
	private final static String userId = "userId";
	private final static String email= "test@test.com";
	
	
	private final static UserDetailsDTO userDetails = UserDetailsDTO.__builder()
			.address(uaddress)
			.firstName(firstName)
			.lastName(lastName)
			.phone(phone)
			.city(city)
			.build();
	
	private final static UserDTO user = new UserDTO(userId, email, null);
	
	private final static Courier courier = CourierService.userToCourier(user, userDetails);
	
	
	@Test
	public void testUserDetailsMerging() {
		Courier c = service.userToCourier(user, userDetails);
		assertEquals(userId, c.getId());
		assertEquals(firstName, c.getFirstName());
		assertEquals(lastName, c.getLastName());
		assertEquals(city, c.getCity());
		assertEquals(phone, c.getPhone());
		assertEquals(uaddress, c.getAddress());
		assertEquals(email, c.getEmail());
	}
	
	@Test
	public void retrieveCourierFromUserTest() {
		when(userService.getDetails(anyString())).thenReturn(userDetails);
		when(userService.get(anyString())).thenReturn(user);
		
		Courier c = service.retrieveCourierFromUser(userId);
		assertEquals(userId, c.getId());
		assertEquals(firstName, c.getFirstName());
		assertEquals(lastName, c.getLastName());
		assertEquals(city, c.getCity());
		assertEquals(phone, c.getPhone());
		assertEquals(uaddress, c.getAddress());
		assertEquals(email, c.getEmail());
	} 
	
	@Test
	public void registerCourierTest() {
		when(userService.getDetails(anyString())).thenReturn(userDetails);
		when(userService.get(anyString())).thenReturn(user);
		when(repository.save(any(Courier.class))).then(returnsFirstArg());
		
		Courier c = service.retrieveCourierFromUser(userId);
		assertEquals(userId, c.getId());
		assertEquals(firstName, c.getFirstName());
		assertEquals(lastName, c.getLastName());
		assertEquals(city, c.getCity());
		assertEquals(phone, c.getPhone());
		assertEquals(uaddress, c.getAddress());
		assertEquals(email, c.getEmail());
	} 
	
	
	@Test
	public void getCouriersTest() {
		List<Courier> list = Arrays.asList(
				courier, 
				from(courier).clone().update(c -> c.setId("second")).get()
				);
		when(repository.findAllByStatus(CourierStatus.free)).thenReturn(list);
		assertEquals(2,service.getCouriers(CourierStatus.free).size());
		
	}

}
