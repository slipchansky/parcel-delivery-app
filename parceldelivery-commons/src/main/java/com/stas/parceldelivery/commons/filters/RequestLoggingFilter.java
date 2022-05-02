package com.stas.parceldelivery.commons.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
	
	public RequestLoggingFilter() {
		super();
        this.setIncludeQueryString(true);
        this.setIncludeClientInfo(false);
        this.setIncludePayload(false);
        this.setMaxPayloadLength(64000);
	}

	private void prepareContext(HttpServletRequest request, HttpServletResponse response) {
		CallContext context = CallContext.getInstance();
		String correlationId = request.getHeader(ParceldeliveryHeaders.CORRELATION_ID);
		String requestId = request.getHeader(ParceldeliveryHeaders.REQUEST_ID);
		if(!StringUtils.hasText(correlationId)) correlationId = UUID.randomUUID().toString();
		if(!StringUtils.hasText(requestId)) requestId = correlationId;
		context.updateContext(correlationId, requestId);
		response.addHeader(ParceldeliveryHeaders.REQUEST_ID, requestId);
		response.addHeader(ParceldeliveryHeaders.CORRELATION_ID, correlationId);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		prepareContext(request, response);
		super.doFilterInternal(request, response, filterChain);
	}
	
}
