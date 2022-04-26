package com.stas.parceldelivery.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ParceldeliveryAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParceldeliveryAdminServiceApplication.class, args);
	}

}
