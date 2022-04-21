package com.stas.parceldelivery.commons.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanMap;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BeanConverter {
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	public static <R> R convert(Object source, Class<R> destinationClass) {
		return OBJECT_MAPPER.convertValue(source, destinationClass);
	}
	
	@AllArgsConstructor
	public static class ___ConversionContext<R> {
		private R bean;
		public <R> R to(Class<R> clazz) {
			return BeanConverter.convert(bean, clazz);
		}
		
		public <R> R with(Object x) {
			if(bean != null && x!=null) {
			 Map dst = new BeanMap(bean);
			 Map src = BeanConverter.convert(x, Map.class);
			 dst.putAll(src);
			}
			return (R)bean;
		}

		public <T> ___ConversionContext<T> convert(Class<T> clazz) {
			return new ___ConversionContext<T>(to(clazz));
		}
		
		public ___ConversionContext<R> update(Consumer<R> action) {
			action.accept(bean);
			return this; 
		}
		
		public R get() {
			return bean;
		}
		
	}
	
	public static <T> ___ConversionContext<T> from(T bean) {
		return new ___ConversionContext<>(bean); 
	}
	
	
	
	
	
}
