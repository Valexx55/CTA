package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;

/**
 * ESTA CLASE SE DEDICA A INTERACTUAR CON LA BASE DE DATOS
 */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	//VAMOS A AÑADIR NUEVAS OPERACIONES EN LA BASE DATOS
	
	//1) KEYWORD QUERIES - Consultas por palabras clave
	
		 //consulta los alumnos que estén en un rango de edad
	     Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax);
	     
	     //consultar los alumnos que contengan un nombre dado 
	     Iterable<Alumno> findByNombreContaining(String name);
	
	//2) JPQL - HQL
	
	//3) NATIVAS 
	
	//4) CRITERIA API 
	
	//5) STORED PROCEDURES 

}
