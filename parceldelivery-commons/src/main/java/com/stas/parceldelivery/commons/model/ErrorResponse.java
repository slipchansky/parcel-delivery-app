package com.stas.parceldelivery.commons.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "__ERB")
@Data
public class ErrorResponse {
	private int status;
	private List<String> message;
	private Date timeStamp = new Date();
	
	public static ErrorResponseBuilder builder() {
		return new ErrorResponseBuilder();
	}
	
	public static class ErrorResponseBuilder extends __ERB {
		public ErrorResponseBuilder exception(MethodArgumentNotValidException exception) {
			super.message(exception.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));
			return this;
		}
		
		public ErrorResponseBuilder message(String message) {
			super.message(Arrays.asList(message));
			return this;
		}

		@Override
		public ErrorResponseBuilder message(List<String> message) {
			super.message(message);
			return this;

		}

		@Override
		public ErrorResponseBuilder status(int status) {
			super.status(status);
			return this;
		}
		

		@Override
		public ErrorResponseBuilder timeStamp(Date timeStamp) {
			super.timeStamp(timeStamp);
			return this;
		}

		public __ERB status(HttpStatus status) {
			super.status(status.ordinal());
			return this;
		}
		
		
	}
	
}
