package edu.cta.academy.alumnos.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.alumnos.repository.entity.Alumno;

/**
 * ESTA CLASE SE DEDICA A INTERACTUAR CON LA BASE DE DATOS
 */
@Repository
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
//public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	//VAMOS A AÑADIR NUEVAS OPERACIONES EN LA BASE DATOS
	
	//1) KEYWORD QUERIES - Consultas por palabras clave
	
		 //consulta los alumnos que estén en un rango de edad
	     Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax);
	     
	   //consulta los alumnos que estén en un rango de edad
	     Page<Alumno> findByEdadBetween(int edadmin, int edadmax, Pageable pageable);
	     
	     //consultar los alumnos que contengan un nombre dado 
	     Iterable<Alumno> findByNombreContaining(String name);
	
	//2) JPQL - HQL // similar a SQL - en vez referinos a las tablas, lo hace a las entidades
	     
	     @Query(value = "SELECT a FROM Alumno a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%")
	     Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
	
	//3) NATIVAS 
	     
	     @Query(value = "SELECT * FROM alumnos a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%", nativeQuery = true)
	     Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	//4) CRITERIA API 
	
	//5) STORED PROCEDURES 
	     
	     //1 DEFINID LOS PROCEDIMIENTOS EN BASE DE DATOS X
	     //2 REFERENCIAMOS ESOS PROCEDIMIENTOS EN LA ENTIDAD ALUMNO x
	     //3 HACEMOS MÉTODOS EN ALUMNO REPOSITORY @Procedure QUE USEN EL APARTADO 2
	     @Procedure(name = "Alumno.alumnosRegistradosHoy")
	     Iterable<Alumno> procedimientoAltaAlumnosHoy();
	     
	     @Procedure(name = "Alumno.alumnosEdadMediaMinMax")
	     Map<String, Number> procedimientoEstadisticosEdad(int edadmax, int edadmin, float edadmedia);
	     

}
