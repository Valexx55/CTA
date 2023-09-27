package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsalumnosprofeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosprofeApplication.class, args);
	}

}
