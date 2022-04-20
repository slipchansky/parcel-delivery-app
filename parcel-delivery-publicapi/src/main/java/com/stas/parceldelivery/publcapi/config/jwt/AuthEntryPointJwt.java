package com.stas.parceldelivery.publcapi.config.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stas.parceldelivery.commons.model.ErrorResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.err.println(authException.getMessage());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		ErrorResponse error = ErrorResponse.builder()
				.status(HttpServletResponse.SC_UNAUTHORIZED)
				.messages("Unauthorized", request.getServletPath(), authException.getMessage())
				.build();

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), error);
	}

}
