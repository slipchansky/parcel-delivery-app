package com.stas.parceldelivery.publcapi.config.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;
import com.stas.parceldelivery.publcapi.service.auth.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	private static final long LIMIT_CACHE_TTL_MIN = 10;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private Cache<String, UsernamePasswordAuthenticationToken> cachedTokens;

	@PostConstruct
	public void init() {
		cachedTokens = CacheBuilder.newBuilder().expireAfterAccess(LIMIT_CACHE_TTL_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, UsernamePasswordAuthenticationToken>() {
					@Override
					public UsernamePasswordAuthenticationToken load(String jwt) throws Exception {
						String username = jwtUtils.getUserNameFromJwtToken(jwt);
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					}
				});
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		prepareContext(request, response);
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				UsernamePasswordAuthenticationToken authenticationToken = cachedTokens.get(jwt);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				updateContext();
			}
		} catch (Exception e) {
			log.error("Error in process of authentication", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
			return headerAuth.substring(BEARER_PREFIX.length(), headerAuth.length());
		}

		return null;
	}
	
	private void prepareContext(HttpServletRequest request, HttpServletResponse response) {
		
		CallContext context = CallContext.getInstance();
		
		String correlationId = request.getHeader(ParceldeliveryHeaders.CORRELATION_ID);
		String requestId = request.getHeader(ParceldeliveryHeaders.REQUEST_ID);
		
		
		if(!StringUtils.hasText(correlationId)) correlationId = UUID.randomUUID().toString();
		if(!StringUtils.hasText(requestId)) requestId = correlationId;
		
		context.setCorrelationId(correlationId);
		context.setRequestId(requestId);
		
		response.addHeader(ParceldeliveryHeaders.REQUEST_ID, requestId);
		response.addHeader(ParceldeliveryHeaders.CORRELATION_ID, correlationId);
	}

	private void updateContext() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		CallContext context = CallContext.getInstance();
		String userName = securityContext.getAuthentication().getName();
		List<String> roles = securityContext
				.getAuthentication()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::toString)
				.collect(Collectors.toList());
		context.setUserId(userName);
		context.setRoles(roles);
	}

}
