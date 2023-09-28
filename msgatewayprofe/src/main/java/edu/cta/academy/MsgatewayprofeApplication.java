package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsgatewayprofeApplication {

	/**
	 * PASOS PARA CREAR GATEWAY
	 * 
	 * 1) Nuevo Spring starter project -> add gateway y eureka client
	 * 2) Anotación @EnableEurekaClient en el main
	 * 3) Properties / yml
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(MsgatewayprofeApplication.class, args);
	}

}
