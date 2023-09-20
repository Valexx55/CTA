package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;

/**
 * ESTA CLASE SE DEDICA A INTERACTUAR CON LA BASE DE DATOS
 */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	

}
