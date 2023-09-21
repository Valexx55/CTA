package edu.cta.academy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;

/**
 * ESTA CLASE, RECIBE LAS PETICIONES DEL CLIENTE
 * Y LE CONTESTA
 */

@RestController
@RequestMapping("/alumno") //servidor, todo lo que sea /alumno, es para esta clase
public class AlumnoController {
	
	//ALTA
	//BAJA
	//CONSULTAR UNO
	//CONSULTAR TODOS
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos() // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;
		
		return responseEntity;
	}
	//MODIFICAR

}
