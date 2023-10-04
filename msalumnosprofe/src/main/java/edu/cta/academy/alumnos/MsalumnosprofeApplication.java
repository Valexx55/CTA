package edu.cta.academy.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@ComponentScan//para indicar el paquete/ubicacion de Service/Repository/Controller si estuvieran fuera del directorio raíz
@SpringBootApplication
@EnableEurekaClient
@EntityScan("edu.cta.academy.comun")//Así Spring busca las entidades en ese paquete - SI NO, sólo busca en el paquete raíz edu.cta.academy.alumnos
@EnableFeignClients//Activamos el FeignClient
public class MsalumnosprofeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosprofeApplication.class, args);
	}

}
