package com.stas.parceldelivery.publcapi.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stas.parceldelivery.commons.exceptions.AlreadyExistsException;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorResponse error = ErrorResponse.builder().exception(exception).status(status.value()).build();

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleAllExceptions(NotFoundException ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().messages(ex.getMessage()).status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

	}

	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().messages(ex.getMessage()).status(HttpStatus.BAD_REQUEST)
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(AlreadyExistsException.class)
	public final ResponseEntity<Object> handleBadRequest(AlreadyExistsException ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().messages(ex.getMessage()).status(HttpStatus.CONFLICT).build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimeStamp(new Date());
		List<String> errors = new ArrayList<>();
		errors.add(ex.getLocalizedMessage());
		error.setMessage(errors);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setTimeStamp(new Date());
		error.setMessage(Arrays.asList("Unauthorized"));
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

	}
	
	
	

}