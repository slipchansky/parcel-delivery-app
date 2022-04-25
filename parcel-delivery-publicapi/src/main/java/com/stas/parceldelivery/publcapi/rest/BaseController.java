package com.stas.parceldelivery.publcapi.rest;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		return CallContext.getInstance();
	}
	
	protected <R> R call( Function<CallContext, R> f) {
		return f.apply(getCallContext());
	}

}
