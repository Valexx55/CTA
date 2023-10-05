package edu.cta.academy.alumnos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.cta.academy.alumnos.service.AlumnoService;
import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.util.UtilTest;

@WebMvcTest(AlumnoController.class)//con esto, levanto solo el contexto de MicroServicio (no todo)
public class AlumnoControllerTest3 {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	AlumnoService alumnoService; //simulamos el comportamiento del objeto service
	
	@Autowired
	ObjectMapper om;
	
	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void getServicioMockeado() throws Exception {
		
		Alumno alumno = new Alumno();
		alumno.setNombre("JUAN");
		alumno.setApellido("PEREZ");
		alumno.setEdad(36);
		alumno.setEmail("juan@zgz.es");
		
		ObjectNode objectNode = om.createObjectNode();
		objectNode.put( "nombre", "JUAN");
		objectNode.put( "edad", "36");
		objectNode.put( "apellido", "PEREZ");
		objectNode.put( "email", "juan@zgz.es");
		
		String alumno_json = objectNode.toString();
		
		//programamos el funcionamiento del mock
		when(alumnoService.consultarPorId(1l)).thenReturn(Optional.of(alumno));

		this.mockMvc.perform(get("/alumno/1")).
		andDo(print()).
		andExpect(status().isOk()).
		andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("JUAN")).
		andExpect(MockMvcResultMatchers.jsonPath("$.edad").value("36"));
	}
	
	
	
}
