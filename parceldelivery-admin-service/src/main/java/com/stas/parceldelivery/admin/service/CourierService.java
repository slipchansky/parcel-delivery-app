package com.stas.parceldelivery.admin.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.repository.CourierRepository;
import com.stas.parceldelivery.commons.enums.CourierStatus;
import com.stas.parceldelivery.commons.model.CourierDTO;
import com.stas.parceldelivery.commons.model.SecurityUserResponseDTO;
import com.stas.parceldelivery.commons.model.UserDetailsDTO;
import static com.stas.parceldelivery.commons.util.BeanConverter.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CourierService {
	
	
	@Autowired
	UserServiceClient userService;

	
	
	@Autowired 
	CourierRepository repository;
	
	public CourierDTO registerCourier(String userId) {
		Courier courier = retrieveCourierFromUser(userId);
		courier.setStatus(CourierStatus.free);
		Courier saved = repository.save(courier);
		CourierDTO result = from(saved).to(CourierDTO.class);
		return result;
		
	}

	Courier retrieveCourierFromUser(String userId) {
		SecurityUserResponseDTO user = userService.get(userId);
		UserDetailsDTO details = userService.getDetails(userId);
		Courier courier = userToCourier(user, details);
		return courier;
	}

	static Courier userToCourier(SecurityUserResponseDTO user, UserDetailsDTO details) {
		Courier courier = from(details).to(Courier.class);
		courier = from(courier).with(user);
		courier.setId(user.getUsername());
		return courier;
	}
	
	public List<CourierDTO> getCouriers(CourierStatus status){
		return repository.findAllByStatus(status).stream()
				.map(c -> from(c).to(CourierDTO.class))
				.collect(Collectors.toList());
	}
	
	public List<CourierDTO> getAllCouriers(){
		return StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(c -> from(c).to(CourierDTO.class))
				.collect(Collectors.toList());
	}
	

}
