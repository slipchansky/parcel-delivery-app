package com.stas.parceldelivery.courier.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stas.parceldelivery.commons.amqp.aspects.AmqlListenerAspect;

@Configuration
public class AspectConfiguration {
	
	@Bean
	public AmqlListenerAspect amqlListenerAspect() {
		return new AmqlListenerAspect();
		
	}

}
