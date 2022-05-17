package com.stas.parceldelivery.publcapi.filters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stas.parceldelivery.commons.exchange.CallContext;
import com.stas.parceldelivery.publcapi.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private JwtUtils jwtUtils;
	
	
	private String extractJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
			return headerAuth.substring(BEARER_PREFIX.length(), headerAuth.length());
		}
		return null;
	}
	

	private PreAuthenticatedAuthenticationToken getAuthenticationToken(String jwt) throws ExecutionException {
		
		PreAuthenticatedAuthenticationToken authenticationToken = null;
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			authenticationToken = jwtUtils.toPreAuthenticatedAuthenticationToken(jwt);
		}
		return authenticationToken;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String jwt = extractJwt(request);
			if (jwt != null) {
				PreAuthenticatedAuthenticationToken authenticationToken = getAuthenticationToken(jwt);
				if(authenticationToken != null) {
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					updateContext(authenticationToken);
				}
			}
		} catch (Exception e) {
			log.error("Authentication error");
			log.trace("Error trace", e);
		}
		
		filterChain.doFilter(request, response);
	}


	private void updateContext(PreAuthenticatedAuthenticationToken authenticationToken) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authenticationToken);
		
		CallContext context = CallContext.getInstance();
		String userName = authenticationToken.getName();
		context.setUserId(userName);
	}

}
