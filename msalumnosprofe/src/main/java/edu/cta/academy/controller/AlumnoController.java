package edu.cta.academy.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;

/**
 * ESTA CLASE, RECIBE LAS PETICIONES DEL CLIENTE
 * Y LE CONTESTA
 */

@RestController
@RequestMapping("/alumno") //servidor, todo lo que sea /alumno -prefijo-, es para esta clase
public class AlumnoController {
	
	
	@Autowired
	AlumnoService alumnoService;
	
	//ALTA
	@PostMapping //POST http://localhost:8081/alumno
	public ResponseEntity<?> insertarAlumno(@RequestBody Alumno alumno) // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;
		
			alumno_nuevo = this.alumnoService.alta(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
		
		return responseEntity;
	}
	
	//BAJA
	
	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno
	public ResponseEntity<?> borrarAlumno(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		
			this.alumnoService.borrarPorId(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK).build();
		
		return responseEntity;
	}
	
	//CONSULTAR UNO
	@GetMapping("/{id}") //GET http://localhost:8081/alumno/5
	public ResponseEntity<?> listarAlumnoPorId(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;//alumno
		
			oa =  this.alumnoService.consultarPorId(id);
			
			if (oa.isEmpty())
			{
				//si no está--devolver el cuerpo vacío y 204 no content
				responseEntity = ResponseEntity.noContent().build();
			}  else {
				//el optional tiene un alumno //si está--devolver el alumno y 200 ok
				Alumno alumno_leido = oa.get();
				responseEntity = ResponseEntity.ok(alumno_leido);
			}
			
		return responseEntity;
	}
	//CONSULTAR TODOS
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos() // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;//lista de alumnos
		
			ita =  this.alumnoService.consultarTodos();
			responseEntity = ResponseEntity.ok(ita);//ita es el cuerpo
		
		return responseEntity;
	}
	//MODIFICAR
	
	//MÉTODO QUE DEVUELVA AL CLIENTE UN ALUMNO DE PRUEBA (NO DE LA BASE DE DATOS)
	
	@GetMapping("/obtener-alumno-test") //GET http://localhost:8081/alumno/obtener-alumno-test
	public Alumno obtenerAlumnoTest ()
	{
		Alumno alumno = null;
		
			//alumno en estado "Transient" -- no está asociado a la base de datos
			alumno = new Alumno(5l, "FERESHTEH", "LOPEZ", "fere@oracle.es", 18, LocalDateTime.now());
		
		return alumno;
	}
	

}
