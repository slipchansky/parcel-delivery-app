package com.stas.parceldelivery.publcapi.config.jwt;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
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
		cachedTokens = CacheBuilder.newBuilder()
	            .expireAfterAccess(LIMIT_CACHE_TTL_MIN, TimeUnit.MINUTES)
	            .build(new CacheLoader<String, UsernamePasswordAuthenticationToken> () {
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
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				UsernamePasswordAuthenticationToken authenticationToken = cachedTokens.get(jwt);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}catch (Exception e) {
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
}
