package edu.cta.academy.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * ESTA CLASE, RECIBE LAS PETICIONES DEL CLIENTE Y LE CONTESTA
 */

@RestController
@RequestMapping("/alumno") // servidor, todo lo que sea /alumno -prefijo-, es para esta clase
public class AlumnoController {

	Logger logger = LoggerFactory.getLogger(AlumnoController.class);// obtengo instancia de log

	@Autowired
	AlumnoService alumnoService;

	private ResponseEntity<?> obtenerErrores(BindingResult br) {
		ResponseEntity<?> responseEntity = null;

		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach((ObjectError error) -> // esta función en realidad es accept
		{
			logger.error(error.toString());
		});
		// Collections.sort(listaErrores, (o1, o2)->
		// o1.toString().compareTo(o2.toString()));
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	}

	// ALTA
	@Operation(description = "Este método inserta un alumno en la base datos")
	@PostMapping // POST http://localhost:8081/alumno
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) // ResponseEntity
																									// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;

		if (br.hasErrors()) {
			responseEntity = obtenerErrores(br);
		} else {
			logger.debug("El alumno viene sin fallos");
			alumno_nuevo = this.alumnoService.alta(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
		}

		return responseEntity;
	}

	// BAJA

	@DeleteMapping("/{id}") // DELETE http://localhost:8081/alumno
	public ResponseEntity<?> borrarAlumno(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																	// respuesta
	{
		ResponseEntity<?> responseEntity = null;

		String saludo = null;
		saludo.length();

		this.alumnoService.borrarPorId(id);
		responseEntity = ResponseEntity.status(HttpStatus.OK).build();

		return responseEntity;
	}

	// CONSULTAR UNO
	@GetMapping("/{id}") // GET http://localhost:8081/alumno/5
	public ResponseEntity<?> listarAlumnoPorId(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																		// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		oa = this.alumnoService.consultarPorId(id);

		if (oa.isEmpty()) {
			// si no está--devolver el cuerpo vacío y 204 no content
			responseEntity = ResponseEntity.noContent().build();
		} else {
			// el optional tiene un alumno //si está--devolver el alumno y 200 ok
			Alumno alumno_leido = oa.get();
			responseEntity = ResponseEntity.ok(alumno_leido);
		}

		return responseEntity;
	}

	// CONSULTAR TODOS
	@GetMapping // GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos() // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;// lista de alumnos

		/*
		 * var nombre = "HOLA"; nombre.charAt(4);
		 */

		ita = this.alumnoService.consultarTodos();
		responseEntity = ResponseEntity.ok(ita);// ita es el cuerpo

		return responseEntity;
	}
	// MODIFICAR

	@PutMapping("/{id}") // PUT http://localhost:8081/alumno/5
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br, @PathVariable Long id) // ResponseEntity
																															// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		if (br.hasErrors()) {
			logger.debug("Alumno con errores en PUT");
			responseEntity = obtenerErrores(br);

		} else {

			logger.debug("ALUMNO RX " + alumno);
			oa = this.alumnoService.modificarPorId(alumno, id);

			if (oa.isEmpty()) {
				// si no está--devolver el cuerpo vacío y 404 no content
				responseEntity = ResponseEntity.notFound().build();
			} else {
				// el optional tiene un alumno //si está--devolver el alumno y 200 ok
				Alumno alumno_modificado = oa.get();
				responseEntity = ResponseEntity.ok(alumno_modificado);
			}

		}

		return responseEntity;
	}

	// MÉTODO QUE DEVUELVA AL CLIENTE UN ALUMNO DE PRUEBA (NO DE LA BASE DE DATOS)

	@GetMapping("/obtener-alumno-test") // GET http://localhost:8081/alumno/obtener-alumno-test
	public Alumno obtenerAlumnoTest() {
		Alumno alumno = null;

		// alumno en estado "Transient" -- no está asociado a la base de datos
		alumno = new Alumno(5l, "FERESHTEH", "LOPEZ", "fere@oracle.es", 18, LocalDateTime.now());

		return alumno;
	}
	
	
	
	@GetMapping("/buscarPorRangoEdad") // GET //http://localhost:8081/alumno/buscarPorRangoEdad?edadmin=3&edadmax=90
	public ResponseEntity<?> listarAlumnosEnRangoDeEdad(
			@RequestParam(required = true, name = "edadmin") int edadmin, 
			@RequestParam(required = true, name = "edadmax") int edadmax) // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;// lista de alumnos

			ita = this.alumnoService.findByEdadBetween(edadmin, edadmax);
			responseEntity = ResponseEntity.ok(ita);// ita es el cuerpo

		return responseEntity;
	}

}
