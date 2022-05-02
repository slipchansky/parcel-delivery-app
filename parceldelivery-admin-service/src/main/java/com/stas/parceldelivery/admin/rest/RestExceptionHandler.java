package com.stas.parceldelivery.admin.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stas.parceldelivery.commons.exceptions.AlreadyExistsException;
import com.stas.parceldelivery.commons.exceptions.BadRequestException;
import com.stas.parceldelivery.commons.exceptions.ErrorFromUnderlyingService;
import com.stas.parceldelivery.commons.exceptions.NotFoundException;
import com.stas.parceldelivery.commons.model.ErrorResponse;




@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

    	ErrorResponse error = ErrorResponse.builder()
    			.exception(exception)
    			.status(status.value())
    			.build();
    	
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(NotFoundException ex, WebRequest request) {
    	ErrorResponse response = ErrorResponse.builder()
    			.message(ex.getMessage())
    			.status(HttpStatus.NOT_FOUND)
    			.build();
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
    	ErrorResponse response = ErrorResponse.builder()
    			.message(ex.getMessage())
    			.status(HttpStatus.BAD_REQUEST)
    			.build();
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    
    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<Object> handleBadRequest(AlreadyExistsException ex, WebRequest request) {
    	ErrorResponse response = ErrorResponse.builder()
    			.message(ex.getMessage())
    			.status(HttpStatus.CONFLICT)
    			.build();
    	return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    
	Optional<ErrorResponse> findRootCauseResponse(Throwable cursor) {
		while (cursor.getClass() != Exception.class) {
			if (cursor instanceof ErrorFromUnderlyingService) {
				return Optional.of(((ErrorFromUnderlyingService) cursor).getResponse());
			} 
			cursor = cursor.getCause();
		}
		return Optional.empty();
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {


		ErrorResponse error;
		
		Optional<ErrorResponse> possibleCause = findRootCauseResponse(ex);
		if (possibleCause.isPresent()) {
			error = possibleCause.get();
		} else {
			error = new ErrorResponse();
			error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			List<String> errors = new ArrayList<>();
			errors.add(ex.getLocalizedMessage());
			error.setMessage(errors);
		}
		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.resolve(error.getStatus()));
	}

}
