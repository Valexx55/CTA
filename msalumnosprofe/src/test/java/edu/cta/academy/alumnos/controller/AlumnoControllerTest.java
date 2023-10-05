package edu.cta.academy.alumnos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import edu.cta.academy.comun.entity.Alumno;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //con esto lanzamos el servidor en un puerto aleatorio
public class AlumnoControllerTest {

	@LocalServerPort
	private int port; //para inyectar el puerto donde se realiza la prueba
	
	@Autowired
	TestRestTemplate template;//para lanzar peticiones
	
	@Test //con esta anotación, indico que esto es un caso de prueba/test
	public void testGetAlumnos()
	{
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).doesNotContainNull();
	}
	
	@Test //con esta anotación, indico que esto es un caso de prueba/test
	public void testGetAlumnosNull()
	{
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).containsOnlyNulls();
	}
	
	//assertThat(la).allMatch(p -> p.getId() != 0);
	
	@Test //con esta anotación, indico que esto es un caso de prueba/test
	public void testGetAlumnosIdMayorQueCero()
	{
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).allMatch(p -> p.getId() >= 0);
	}
	
	//TODO hacemos un test que compruebe que todos los ids son disintos de 0 con una lambda
	
	@BeforeAll //para ejecutar antes de todos los métodos TEST
	public static void antesDeTodosLosTests()
	{
		System.out.println("antesDeTodosLosTests()");
	}
	
	@AfterAll//para ejecutar después de todos los métodos TEST
	public static void despuesDeTodosLosTests()
	{
		System.out.println("despuesDeTodosLosTests()");
	}
	
	@BeforeEach//para ejecutar antes de cada  método TEST
	public void antesDeCadaTest ()
	{
		System.out.println("antesDeCadaTest ()");
	}
	
	@AfterEach//para ejecutar después de cada  método TEST
	public void despuesDeCadaTest ()
	{
		System.out.println("despuesDeCadaTest ()");
	}
}
