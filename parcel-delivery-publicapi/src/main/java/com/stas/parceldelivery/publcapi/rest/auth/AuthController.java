package com.stas.parceldelivery.publcapi.rest.auth;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stas.parceldelivery.publcapi.config.jwt.JwtUtils;
import com.stas.parceldelivery.publcapi.constants.Headers;
import com.stas.parceldelivery.publcapi.dto.JwtResponse;
import com.stas.parceldelivery.publcapi.dto.LoginRequest;
import com.stas.parceldelivery.publcapi.dto.SignUpResponse;
import com.stas.parceldelivery.publcapi.dto.SignupRequest;
import com.stas.parceldelivery.publcapi.exceptions.UserAlreadyExistsException;
import com.stas.parceldelivery.publcapi.service.UserService;
import com.stas.parceldelivery.publcapi.service.auth.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	@FunctionalInterface
	public interface SignupPerformer {
		public SignUpResponse perform() throws UserAlreadyExistsException;
	}

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;
	
	

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(
				new JwtResponse(jwt, JwtResponse.TOKEN_TYPE, userDetails.getUsername(), userDetails.getEmail(), roles));
	}
	
	
	
	public ResponseEntity<SignUpResponse> createUser(SignupPerformer performer)  {
		try {
			SignUpResponse payload = performer.perform();
			return ResponseEntity.ok(payload);
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new SignUpResponse("Username is exist"));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new SignUpResponse("Internal server error"));
		}
	} 
	

	 
	@PostMapping("/admin/new")
	public ResponseEntity<SignUpResponse> registerAdmin(@RequestBody SignupRequest signupRequest) {
		return createUser(()->userService.createAdmin(signupRequest));
	}
	
	@PostMapping("/client/new")
	public ResponseEntity<SignUpResponse> registerClient(@RequestBody SignupRequest signupRequest) {
		return createUser(()->userService.createClient(signupRequest));
	}
	
}
