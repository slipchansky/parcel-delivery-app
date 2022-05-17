package com.stas.parceldelivery.publcapi.utils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.publcapi.service.auth.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.claim("authorities", authentication.getAuthorities().stream().map(a -> a.toString()).collect(Collectors.toSet()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public boolean validateJwtToken(String jwt) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
			return true;
		} catch (MalformedJwtException e) {
			log.error(e.getMessage(), e);
			log.trace("stack trace");
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.trace("stack trace", e);
		}

		return false;
	}


	public PreAuthenticatedAuthenticationToken toPreAuthenticatedAuthenticationToken(String jwt) {
		Jws<Claims> claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(jwt);
		String username = claims.getBody().getSubject();
		Collection<String> sAuthorities = (Collection<String>)claims.getBody().get("authorities");
		List<SimpleGrantedAuthority> authorities = sAuthorities.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
		return new PreAuthenticatedAuthenticationToken(username, null,  authorities);
	}

}
