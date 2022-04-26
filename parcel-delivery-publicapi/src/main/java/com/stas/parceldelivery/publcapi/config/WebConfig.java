package com.stas.parceldelivery.publcapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stas.parceldelivery.commons.filters.RequestLoggingFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class WebConfig implements WebMvcConfigurer {                                    
    

    
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
    	return new RequestLoggingFilter();
    }
    
}