package edu.cta.academy.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.comun.entity.Curso;

@Repository//opcional
public interface CursoRespository extends CrudRepository<Curso, Long> {

	
	//ADD Query Nativa, que dado un ID de Alumno
	//nos diga a qué curso está asignado
	
	@Query(value = "select * from cursos where id = "
			+ "(select curso_id from cursos_alumnos where alumnos_id = ?1)", nativeQuery = true)
	public Optional<Curso> obtenerCursoAlumnoNativa (Long id_alumno);
}
