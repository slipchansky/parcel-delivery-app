package com.stas.parceldelivery.commons.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.beanutils.BeanMap;

import com.fasterxml.jackson.annotation.JsonInclude;


/*
 * Here is a trick for transferring the values of same-named fields between beans.
 * I realize that this trick in general may cause the issues but in real life I would introduce additional unit 
 * tests for fixing the matching of beans by their fields. 
 * */
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
	
	public static <R> R copy(R source) {
		return (R)OBJECT_MAPPER.convertValue(source, source.getClass());
	}
	
	
	
	@AllArgsConstructor
	public static class ConversionContext<R> {
		private R bean;
		public <R> R to(Class<R> clazz) {
			return BeanConverter.convert(bean, clazz);
		}

		// peforms partial update from given bean to target bean. 
		// Only not null fields are being transferred
		public <T> R with(T x) {
			if(bean != null && x!=null) {
			 BeanMap dst = new BeanMap(bean);
			 Object o = BeanConverter.convert(x, bean.getClass());
			 BeanMap src = new BeanMap(o);
			 Set<Entry<Object, Object>> entries = src.entrySet();
			 
			 // stas. improve this
			 entries.forEach(e -> {
				 if(!"class".equals(e.getKey()) && e.getValue() != null)
					 dst.put(e.getKey(), e.getValue());
			 });
			 
			}
			return (R)bean;
		}

		public <T> ConversionContext<T> convert(Class<T> clazz) {
			return new ConversionContext<T>(to(clazz));
		}
		
		public ConversionContext<R> update(Consumer<R> action) {
			action.accept(bean);
			return this; 
		}
		
		public R get() {
			return bean;
		}
		
		public ConversionContext<R> clone() {
			this.bean = (R)BeanConverter.convert(bean, bean.getClass());
			return this;
		}
		
	}
	
	public static <T> ConversionContext<T> from(T bean) {
		return new ConversionContext<>(bean); 
	}
}
