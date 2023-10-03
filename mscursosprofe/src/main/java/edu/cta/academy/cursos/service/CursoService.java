package edu.cta.academy.cursos.service;

import java.util.List;
import java.util.Optional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

public interface CursoService {
	
	Iterable<Curso> findAll();
	
	Optional<Curso> findById(Long id);
	
	Curso save (Curso curso);
	
	void deleteById (Long id);
	
	Optional<Curso> update (Curso curso, Long id);
	
	Optional<Curso> asignarAlumnos (List<Alumno> alumnos, Long id);
	
	Optional<Curso> eliminarAlumno (Alumno alumno, Long id);
	
}
