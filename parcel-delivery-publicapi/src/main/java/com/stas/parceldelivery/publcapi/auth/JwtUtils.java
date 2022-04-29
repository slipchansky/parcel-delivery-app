package com.stas.parceldelivery.publcapi.auth;

import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.stas.parceldelivery.publcapi.service.auth.UserSecurityDetailsImpl;

import io.jsonwebtoken.Claims;
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

		UserSecurityDetailsImpl userPrincipal = (UserSecurityDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
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

	public String getUserNameFromJwtToken(String jwt) {
		Claims jvtBody = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();
		return jvtBody.getSubject();
	}

}
