package com.stas.parceldelivery.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ParceldeliveryClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParceldeliveryClientServiceApplication.class, args);
	}

	@Value("${eureka.client.serviceUrl.defaultZone}")
	private String discoveryUrl;
	
	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.profiles.active}")
	private String profile;
	
	@Value("${spring.rabbitmq.host}")
	private String amqpHost;
	
	@Value("${spring.rabbitmq.port}")
	private String amqpPort;
	
	
	@PostConstruct
	public void showConfig() {
		log.info("profile = {}", profile);
		log.info("eureka.client.serviceUrl.defaultZone = {}", discoveryUrl);
		log.info("spring.datasource.url = {}", datasourceUrl);
		
		log.info("rabbitmq.host = {}", amqpHost);
		log.info("rabbitmq.port = {}", amqpPort);
	}
	
}
