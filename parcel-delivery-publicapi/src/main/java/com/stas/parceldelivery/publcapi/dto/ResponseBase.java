package com.stas.parceldelivery.publcapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public abstract class ResponseBase {
	private String message;
	
}
