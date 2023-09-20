package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.repository.AlumnoRepository;
import edu.cta.academy.repository.entity.Alumno;

/**
 * ESTA CLASE REALIZA LAS TAREAS DE LA APLICACION DEFINIDAS EN ALUMNOSERVICE
 */

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired //con esto, consigo que el alumnoRepository instanciado automáticamte por spring, esté aquí
	AlumnoRepository alumnoRepository;

	@Override
	@Transactional
	public Alumno alta(Alumno alumno) {
		return this.alumnoRepository.save(alumno);
	}

	@Override
	@Transactional
	public void borrarPorId(Long id) {
		this.alumnoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<Alumno> modificarPorId(Alumno alumno, Long id) {
		//TODO pendiente-personalizado porque no existe un update en el CrudRepository
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)//optimizo para no bloquear la tabla. permito el acceso concurrente "lectura sucia"
	public Optional<Alumno> consultarPorId(Long id) {
		return this.alumnoRepository.findById(id);
		//return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)//optimizo para no bloquear la tabla. permito el acceso concurrente "lectura sucia"
	public Iterable<Alumno> consultarTodos() {
		
		return this.alumnoRepository.findAll();
	}

}
