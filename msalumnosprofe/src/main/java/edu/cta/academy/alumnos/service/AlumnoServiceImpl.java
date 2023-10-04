package edu.cta.academy.alumnos.service;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.cta.academy.alumnos.cliente.CursoFeignClient;
import edu.cta.academy.alumnos.model.FraseChiquito;
import edu.cta.academy.alumnos.repository.AlumnoRepository;
import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

/**
 * ESTA CLASE REALIZA LAS TAREAS DE LA APLICACION DEFINIDAS EN ALUMNOSERVICE
 */

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired //con esto, consigo que el alumnoRepository instanciado automáticamte por spring, esté aquí
	AlumnoRepository alumnoRepository;
	
	@Autowired
	CursoFeignClient cursoFeignClient; //usaremos este objeto para saber a qué curso pertenece un alumno

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
		Optional<Alumno> optional = Optional.empty();
		
			//LEO EL REGISTRO ID
			optional = this.alumnoRepository.findById(id);
		    //SI EXISTE, ACTUALIZO
			if (optional.isPresent())
			{
				//actualizo todos los campos, menos el id y la fecha
				Alumno alumnoLeido = optional.get();
				if (em.contains(alumnoLeido))
				{
					System.out.println("AlumnoLeido en persistencia");
				}
				//alumnoLeido.setNombre(alumno.getNombre());
				//alumnoLeido está en estado Persitente - JPA --> si modifico su estado, se modifica en la BD
				BeanUtils.copyProperties(alumno, alumnoLeido, "id", "creadoEn");
				//this.alumnoRepository.save(alumnoLeido);//No es necesario
				optional = Optional.of(alumnoLeido);//"relleno el huevo"
				
			}
		    //SI NO EXISTE, NO HAGO NADA
		
		return optional;
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

	@Override
	@Transactional(readOnly = true)//optimizo para no bloquear la tabla. permito el acceso concurrente "lectura sucia"
	public Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax) {
		
		return this.alumnoRepository.findByEdadBetween(edadmin, edadmax);
	}
	
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findByNombreContaining(String name) {
		return this.alumnoRepository.findByNombreContaining(name);
	}
	
	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa(String patron) {
		return this.alumnoRepository.busquedaPorNombreOApellidoNativa(patron);
	}

	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> busquedaPorNombreOApellidoJPQL(String patron) {
		return this.alumnoRepository.busquedaPorNombreOApellidoJPQL(patron);
	}

	@Override
	@Transactional //no indicamos ReadOnly a true porque en los procedimientos NO VA
	public Iterable<Alumno> procedimientoAltaAlumnosHoy() {
		return this.alumnoRepository.procedimientoAltaAlumnosHoy();
	}

	@Override
	@Transactional
	public Map<String, Number> procedimientoEstadisticosEdad() {
		
		return this.alumnoRepository.procedimientoEstadisticosEdad(0, 0, 0f);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll(Pageable pageable) {
		
		return this.alumnoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax, Pageable pageable) {
		return this.alumnoRepository.findByEdadBetween(edadmin, edadmax, pageable);
	}

	@Override
	public Optional<FraseChiquito> obtenerFraseAleatoriaChiquito() {
		Optional<FraseChiquito> oc = Optional.empty();
		RestTemplate restTemplate = null;
		FraseChiquito frase = null;
			
			restTemplate = new RestTemplate();
			frase = restTemplate.getForObject("https://chiquitadas.es/api/quotes/avoleorrr", FraseChiquito.class);
			oc  = Optional.of(frase);
		
		return oc;
	}

	@Override
	public Optional<Curso> obtenerCursoAlumno(Long idalumno) {
		Optional<Curso> oc = Optional.empty();
		
			oc = this.cursoFeignClient.obtenerCursoAlumno(idalumno);
			
		return oc;
	}
	
	
	

}
