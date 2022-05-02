package com.stas.parceldelivery.commons.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "__ERB")
@Data
@ApiModel(value = "Common Error Response")
public class ErrorResponse {
	@ApiModelProperty(value = "Http Status")
	private int status;
	@ApiModelProperty(value = "Error Message")
	private List<String> message;
	
	@ApiModelProperty(value = "When the Error occured")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss Z")
	private Date timeStamp = new Date();
	
	public static ErrorResponseBuilder builder() {
		return new ErrorResponseBuilder();
	}
	
	public static class ErrorResponseBuilder extends __ERB {
		
		public ErrorResponseBuilder() {
			super();
			super.timeStamp(new Date());
		}
		
		public ErrorResponseBuilder exception(MethodArgumentNotValidException exception) {
			super.message(exception.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));
			return this;
		}
		
		
		public ErrorResponseBuilder messages(String ...messages ) {
			super.message(Arrays.asList(messages));
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

		public ErrorResponseBuilder status(HttpStatus status) {
			super.status(status.value());
			return this;
		}


		public ErrorResponseBuilder message(String message) {
			return messages(message);
		}
		
		
	}
	
}
