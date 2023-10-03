package edu.cta.academy.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import edu.cta.academy.alumnos.model.FraseChiquito;
import edu.cta.academy.comun.entity.Alumno;

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
    
    Iterable<Alumno> findAll (Pageable pageable);
    
    Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax, Pageable pageable);
    
    //NOTA: No tocaría definirlo aquí
    Optional<FraseChiquito> obtenerFraseAleatoriaChiquito ();

}
