package com.stas.parceldelivery.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stas.parceldelivery.commons.filters.RequestLoggingFilter;

@Configuration
public class FilterConfiguration {
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
    	return new RequestLoggingFilter();
    }
}
