package com.stas.parceldelivery.publcapi.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		int k =0;
		k++;
//		if (!(authentication instanceof JWTAuthenticationToken)) {
//			return;
//		}
//
//		JWTAuthenticationToken jwtAuthenticaton = (JWTAuthenticationToken) authentication;
//
//		// Add a session cookie
//		Cookie sessionCookie = new Cookie("someSessionId", jwtAuthenticaton.getToken());
//		response.addCookie(sessionCookie);

		 super.onAuthenticationSuccess(request, response, authentication);
	}

}
