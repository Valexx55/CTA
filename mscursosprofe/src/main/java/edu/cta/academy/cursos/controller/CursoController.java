package edu.cta.academy.cursos.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.cursos.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	@Autowired
	CursoService cursoService;

	@PostMapping // POST http://localhost:8082/curso
	public ResponseEntity<?> insertarcurso(@RequestBody Curso curso) // ResponseEntity
																		// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Curso curso_nuevo = null;

		curso_nuevo = this.cursoService.save(curso);
		responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(curso_nuevo);

		return responseEntity;
	}

	// BAJA

	@DeleteMapping("/{id}") // DELETE http://localhost:8082/curso
	public ResponseEntity<?> borrarCurso(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																	// respuesta
	{
		ResponseEntity<?> responseEntity = null;

		this.cursoService.deleteById(id);
		responseEntity = ResponseEntity.status(HttpStatus.OK).build();

		return responseEntity;
	}

	// CONSULTAR UNO
	@GetMapping("/{id}") // GET http://localhost:8082/curso/5
	public ResponseEntity<?> listarCursoPorId(@PathVariable Long id) // ResponseEntity representa el mensaje HTTP de
																		// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> oc = null;// curso

		oc = this.cursoService.findById(id);

		if (oc.isEmpty()) {
			// si no está--devolver el cuerpo vacío y 204 no content
			responseEntity = ResponseEntity.noContent().build();
		} else {
			Curso curso_leido = oc.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		}

		return responseEntity;
	}

	// CONSULTAR TODOS
	@GetMapping // GET http://localhost:8081/curso
	public ResponseEntity<?> listarCursos() // ResponseEntity representa el mensaje HTTP de respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Curso> itc = null;// lista de cursos

		itc = this.cursoService.findAll();
		responseEntity = ResponseEntity.ok(itc);// ita es el cuerpo

		return responseEntity;
	}
	// MODIFICAR

	@PutMapping("/{id}") // PUT http://localhost:8081/curso/5
	public ResponseEntity<?> modificarCurso(@RequestBody Curso curso, @PathVariable Long id) // ResponseEntity
																								// respuesta
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> oc = null;// curso

		oc = this.cursoService.update(curso, id);

		if (oc.isEmpty()) {
			// si no está--devolver el cuerpo vacío y 404 no content
			responseEntity = ResponseEntity.notFound().build();
		} else {
			// el optional tiene un curso //si está--devolver el curso y 200 ok
			Curso curso_modificado = oc.get();
			responseEntity = ResponseEntity.ok(curso_modificado);

		}

		return responseEntity;
	}

}
