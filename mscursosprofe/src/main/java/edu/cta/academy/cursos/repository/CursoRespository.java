package edu.cta.academy.cursos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.cursos.repository.entity.Curso;

@Repository//opcional
public interface CursoRespository extends CrudRepository<Curso, Long> {

}
