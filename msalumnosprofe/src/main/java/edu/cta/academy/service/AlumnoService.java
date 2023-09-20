package edu.cta.academy.service;

import java.util.Optional;

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

}
