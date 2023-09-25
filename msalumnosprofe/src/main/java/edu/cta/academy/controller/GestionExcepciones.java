package edu.cta.academy.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ESTA CLASE ACTUA COMO UN "LISTENER" DE EXCEPCIONES
 * CENTRALAZIMOS EL TRATAMIENTO DE EXCEPCIONES EN ESTA CLASE
 */

@RestControllerAdvice(basePackages = {"edu.cta.academy"})//Todas las excepciones que ocurran en este paquete, las derivas a esta clase
public class GestionExcepciones {
	
	//PARA CADA TIPO DE EXCEPCIÓN QUE QUIERA GESTIONAR, HARÉ UN MÉTODO
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> gestionStringIndexOutOfBoundsException (StringIndexOutOfBoundsException e)
	{
		ResponseEntity<?> responseEntity = null;
		
			String mensaje_error = e.getMessage();
			responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}

	//EmptyResultDataAccessException
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> gestionEmptyResultDataAccessException (EmptyResultDataAccessException e)
	{
		ResponseEntity<?> responseEntity = null;
		
			String mensaje_error = e.getMessage();
			responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> gestionErrorGenerico (Throwable e)
	{
		ResponseEntity<?> responseEntity = null;
		
			String mensaje_error = e.getMessage();
			responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}
}
