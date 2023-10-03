package edu.cta.academy.cursos.service;

import java.util.Optional;

import edu.cta.academy.cursos.repository.entity.Curso;

public interface CursoService {
	
	Iterable<Curso> findAll();
	
	Optional<Curso> findById(Long id);
	
	Curso save (Curso curso);
	
	void deleteById (Long id);
	
	Optional<Curso> update (Curso curso, Long id);

}
