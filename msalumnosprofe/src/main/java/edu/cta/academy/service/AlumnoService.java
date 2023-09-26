package edu.cta.academy.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import edu.cta.academy.repository.entity.Alumno;

//QUÉ HACEMOS
public interface AlumnoService {
	
	//ALTA
	Alumno alta (Alumno alumno);
	//BAJA por id
	void borrarPorId (Long id);
	//MODIFICACION
	Optional<Alumno> modificarPorId (Alumno alumno, Long id);
	//CONUSLTAR UNO
	Optional<Alumno> consultarPorId (Long id); //"huevo kinder"
	//CONUSLTAR TODOS
	Iterable<Alumno> consultarTodos();
	//consultar por rango de edad
	Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax);
	
	Iterable<Alumno> findByNombreContaining(String name);
	
	Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
    Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
    
    Iterable<Alumno> procedimientoAltaAlumnosHoy();
    
    Map<String, Number> procedimientoEstadisticosEdad();

}
