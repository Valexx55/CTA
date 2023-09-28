package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//activamos eureka
public class MseurekaprofeApplication {
	
	/**
	 * 1) CREAMOS SPRING STARTER Y DEPEDENCIA EUREKA SERVER
	 * 2) ADD DEPEDENCIA JAXB
	 * 3) ANOTACION @EnableEurekaServer
	 * 4) Config properties
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MseurekaprofeApplication.class, args);
	}

}
