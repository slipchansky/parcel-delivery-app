package com.stas.parceldelivery.publcapi.resources;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;


public abstract class BaseController {
	
	protected CallContext getCallContext() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String userName = securityContext.getAuthentication().getName();

		HttpServletRequest request = 
		        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
		                .getRequest();
		
		
		
		List<String> roles = securityContext
				.getAuthentication()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::toString)
				.collect(Collectors.toList());
		
		CallContext authContext = CallContext.getInstance();
		String correlationId = request.getHeader(ParceldeliveryHeaders.CORRELATION_ID);
		if(!StringUtils.hasText(correlationId)) correlationId = UUID.randomUUID().toString(); 
		authContext.setCorrelationId(correlationId);
		authContext.setUserId(userName);
		authContext.setRoles(roles);
		return authContext;
	}
	
	protected <R> R call( Function<CallContext, R> f) {
		return f.apply(getCallContext());
	}

}
