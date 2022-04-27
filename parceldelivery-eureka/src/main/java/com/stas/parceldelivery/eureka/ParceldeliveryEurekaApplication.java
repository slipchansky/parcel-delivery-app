package com.stas.parceldelivery.eureka;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class ParceldeliveryEurekaApplication {

	@Value("${eureka.client.serviceUrl.defaultZone}")
	private String discoveryUrl;
	

	@PostConstruct
	public void showConfig() {
		log.info("eureka.client.serviceUrl.defaultZone = {}", discoveryUrl);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ParceldeliveryEurekaApplication.class, args);
	}

}
