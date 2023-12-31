package edu.cta.academy.alumnos.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.cta.academy.alumnos.model.FraseChiquito;
import edu.cta.academy.alumnos.service.AlumnoService;
import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import io.swagger.v3.oas.annotations.Operation;

/**
 * ESTA CLASE, RECIBE LAS PETICIONES DEL CLIENTE Y LE CONTESTA
 */

@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/alumno") // servidor, todo lo que sea /alumno -prefijo-, es para esta clase
public class AlumnoController {

	Logger logger = LoggerFactory.getLogger(AlumnoController.class);// obtengo instancia de log

	@Autowired
	AlumnoService alumnoService;
	
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment environment;

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
			logger.debug("ATENTIDO POR " + nombre_instancia + " PUERTO " + environment.getProperty("local.server.port"));
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
	
	//GET //http://localhost:8081/alumno/contieneNombre/e
	@GetMapping("/contieneNombre/{nombre}")		
	public ResponseEntity<?> listarAlumnosNombre (@PathVariable String nombre) 
	{
		ResponseEntity<?> response = null;
		Iterable<Alumno> resul = null;
		
		resul = this.alumnoService.findByNombreContaining(nombre);
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/consultarNombreApellido/{patron}") // GET http://localhost:8081/alumno/consultarNombreApellido
	public ResponseEntity<?> busquedaPorNombreOApellidoNativa(@PathVariable String patron) { 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;
		
			ita = this.alumnoService.busquedaPorNombreOApellidoNativa(patron);
			responseEntity = ResponseEntity.ok(ita);
			
		return responseEntity;
	}
	
	@GetMapping("/consultarNombreApellidoJQPL/{patron}") // GET http://localhost:8081/alumno/consultarNombreApellidoJQPL
	public ResponseEntity<?> busquedaPorNombreOApellidoJPQL(@PathVariable String patron) { 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;
		
			ita = this.alumnoService.busquedaPorNombreOApellidoJPQL(patron);
			responseEntity = ResponseEntity.ok(ita);
			
		return responseEntity;
	}
	
	@GetMapping("/obtenerAlumnosAltaHoy") // GET http://localhost:8081/alumno/obtenerAlumnosAltaHoy
	public ResponseEntity<?> obtenerAlumnosAltaHoy() { 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;
		
			ita = this.alumnoService.procedimientoAltaAlumnosHoy();
			responseEntity = ResponseEntity.ok(ita);
			
		return responseEntity;
	}
	
	@GetMapping("/obtenerEstadisticosEdad") // GET http://localhost:8081/alumno/obtenerEstadisticosEdad
	public ResponseEntity<?> obtenerEstadisticosEdad() { 
		ResponseEntity<?> responseEntity = null;
		Map<String, Number> mapstats = null;
		
			mapstats = this.alumnoService.procedimientoEstadisticosEdad();
			responseEntity = ResponseEntity.ok(mapstats);
			
		return responseEntity;
	}
	
	//ejemplo de llamada http://localhost:8081/alumno/obtenerAlumnosPorPagina?page=1&size=3&sort=edad,ASC
	@GetMapping("/obtenerAlumnosPorPagina") // GET http://localhost:8081/alumno/obtenerAlumnosPorPagina?page=0&size=2
	public ResponseEntity<?> obtenerAlumnosPorPagina(Pageable pageable) { 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado = null;
		
			listado = this.alumnoService.findAll(pageable);
			responseEntity = ResponseEntity.ok(listado);
			
		return responseEntity;
	}
	
	//http://localhost:8081/alumno/edades-paginado?edadmin=5&edadmax=50&page=0&size=2&sort=edad,ASC
	@GetMapping("/edades-paginado")		
	public ResponseEntity<?> listarAlumnosEdadesPaginado(
			@RequestParam(required = true, name="edadmin") int desde, 
			@RequestParam(required = true, name="edadmax") int hasta,
			Pageable pageable
			) 
	{
		ResponseEntity<?> response = null;
		
		var resul = this.alumnoService.findByEdadBetween(desde, hasta, pageable);
		response = ResponseEntity.ok(resul);
		
		return response;
	}
	
	@GetMapping("/obtenerFraseChiquito") // GET http://localhost:8081/alumno/obtenerFraseChiquito
	public ResponseEntity<?> obtenerFraseChiquito() { 
		ResponseEntity<?> responseEntity = null;
		Optional<FraseChiquito> ofrase = null;
		
			ofrase = this.alumnoService.obtenerFraseAleatoriaChiquito();
			if (ofrase.isPresent())
			{
				responseEntity = ResponseEntity.ok(ofrase.get());
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			
			
		return responseEntity;
	}
	
	//3 MÉTODOS NUEVOS
		//1 POST CON FOTO
	@PostMapping("/crear-con-foto") // POST http://localhost:8081/alumno/crear-con-foto
	public ResponseEntity<?> insertarAlumnoConFoto(@Valid Alumno alumno, BindingResult br, MultipartFile archivo ) throws IOException // ResponseEntity
																									// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;

		if (br.hasErrors()) {
			responseEntity = obtenerErrores(br);
		} else {
			logger.debug("El alumno viene sin fallos");
			
			if (!archivo.isEmpty())
			{
				logger.debug("VIENE CON FOTO");
				try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.error("error al cargar la foto", e);
					throw e;
				}
				
			}
			alumno_nuevo = this.alumnoService.alta(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
			
		}

		return responseEntity;
	}
	
		//2 PUT CON FOTO

	//NOTA: EL NOMBRE del parámetro debe coincidir con el nomber de la clave en la petición
	@PutMapping("/modificar-con-foto/{id}") // PUT http://localhost:8081/alumno/modificar-con-foto/5
	public ResponseEntity<?> modificarAlumno(@Valid Alumno alumno, BindingResult br, MultipartFile archivo  , @PathVariable Long id) throws IOException // ResponseEntity
																															// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		if (br.hasErrors()) {
			logger.debug("Alumno con errores en PUT");
			responseEntity = obtenerErrores(br);

		} else {

			logger.debug("ALUMNO RX " + alumno);
			
			if (!archivo.isEmpty())
			{
				try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("ERROR AL MODIFICAR CON FOTO", e);
					throw e;
				}
			}
			
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
	
		//3 GET FOTO POR ID
	@GetMapping("/obtenerFoto/{id}") // GET http://localhost:8081/alumno/obtenerFoto/5
	public ResponseEntity<?> obtenerFotoAlumnoPorId(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																		// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		oa = this.alumnoService.consultarPorId(id);

		if (oa.isEmpty()) {
			
			// si no está--devolver el cuerpo vacío y 204 no content
			logger.debug("El alumno NO existe ");
			responseEntity = ResponseEntity.noContent().build();
		} else {
			// el optional tiene un alumno //si está--devolver el alumno y 200 ok
			Alumno alumno_leido = oa.get();
			//TODO obtener la foto de ese alumno
			if (alumno_leido.getFoto()!=null)
			{
				logger.debug("El alumno tiene foto");
				//Resource podría ser cualquier archivo
				Resource imagen = new ByteArrayResource(alumno_leido.getFoto());//obtengo la imagen
				responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			} else {
				logger.debug("El alumno existe pero NO tiene foto");
				responseEntity = ResponseEntity.noContent().build();
			}
			
		}

		return responseEntity;
	}
	
	
	@GetMapping("/obtenerCursoAlumnoViaFeign/{id}") // GET http://localhost:8081/alumno/obtenerCursoAlumnoViaFeign/5
	public ResponseEntity<?> obtenerCursoAlumnoViaFeign(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																		// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> oc = null;// curso

		oc = this.alumnoService.obtenerCursoAlumno(id);

		if (oc.isEmpty()) {
			// si no está--devolver el cuerpo vacío y 204 no content
			responseEntity = ResponseEntity.noContent().build();
		} else {
			// el optional tiene un curso //si está--devolver el curso y 200 ok
			Curso curso_leido = oc.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		}

		return responseEntity;
	}

	
	// CONSULTAR TODOS con HATEOAS
		@GetMapping("/hateoas") // GET http://localhost:8081/alumno/hateoas
		public ResponseEntity<?> listarAlumnosHateoas() // ResponseEntity representa el mensaje HTTP de respuesta
		{
			ResponseEntity<?> responseEntity = null;
			Iterable<Alumno> ita = null;// lista de alumnos

			/*
			 * var nombre = "HOLA"; nombre.charAt(4);
			 */
				logger.debug("ATENTIDO POR " + nombre_instancia + " PUERTO " + environment.getProperty("local.server.port"));
				ita = this.alumnoService.consultarTodos();
				for (Alumno a :ita)
				{
					a.add(linkTo(methodOn(AlumnoController.class).listarAlumnoPorId(a.getId())).withSelfRel());
				}
				responseEntity = ResponseEntity.ok(ita);// ita es el cuerpo

			return responseEntity;
		}
	
	

}
