package com.stas.parceldelivery.publcapi.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stas.parceldelivery.commons.exceptions.ErrorFromUnderlyingService;
import com.stas.parceldelivery.publcapi.exceptions.SilentExceptionWrapper;
import com.stas.parceldelivery.publcapi.service.UserServiceClient;


@Service
public class UserExistsService {
	
	@Autowired
	UserServiceClient users;

	public boolean userExists(String userId, String email)  {
		try {
			users.headOfUserExists(userId, email);
			return true;
		} catch (Exception e) {
			if (e instanceof ErrorFromUnderlyingService) {
				ErrorFromUnderlyingService errorFromUnderlyingService = (ErrorFromUnderlyingService)e;
				if (errorFromUnderlyingService.getResponse().getStatus()!=HttpStatus.NOT_FOUND.ordinal()) {
					
					/* Lets RestExceptionHandler care on that */
					if(e instanceof RuntimeException)
						throw (RuntimeException)e;
					
					/* Let wrap that to silence */
					throw new SilentExceptionWrapper(e);
				} 
				
			}
		}
		return false;
	}
	
	

}
