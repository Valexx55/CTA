package edu.cta.academy.alumnos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.util.UtilTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AlumnoControllerTest2 {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper om;
	
	@Test //con esta anotación, indico que esto es un caso de prueba/test
	@WithMockUser(username = "admin" , password = "zaragoza")
	public void insertarAlumno() throws Exception
	{
//		Alumno alumno = new Alumno();
//		alumno.setNombre("JUAN");
//		alumno.setApellido("MORENO");
//		alumno.setEdad(69);
//		alumno.setEmail("juan@zgz.es");
		
		ObjectNode objectNode = om.createObjectNode();
		objectNode.put( "nombre", "JUAN");
		objectNode.put( "edad", "36");
		objectNode.put( "apellido", "PEREZ");
		objectNode.put( "email", "juan@zgz.es");
		
		String alumno_json = objectNode.toString();
		
		//String alumno_json = UtilTest.toJSON(alumno);
		
		this.mockMvc.perform(post("/alumno")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno_json))
			    .andExpect(status().isCreated())
			    .andExpect(content().contentType("application/hal+json"));
		
	}
	
	//TODO HACER UN CASO DE POST CON DATOS INCORRECTOS DEL ALUMNO (QUE NO SUPEREN LA VALIDACION)
	
	
}
