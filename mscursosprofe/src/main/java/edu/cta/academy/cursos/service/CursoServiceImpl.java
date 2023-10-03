package edu.cta.academy.cursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.cursos.repository.CursoRespository;

@Service
@Transactional//afecta a todos los métodos de la clase
public class CursoServiceImpl implements CursoService {

	@Autowired
	CursoRespository cursoRespository;
	
	//TODO CursoController terminar el service probar

	@Override
	public Iterable<Curso> findAll() {
		
		return this.cursoRespository.findAll();
	}

	@Override
	public Optional<Curso> findById(Long id) {
		// TODO Auto-generated method stub
		return this.cursoRespository.findById(id);
	}

	@Override
	public Curso save(Curso curso) {
		
		return this.cursoRespository.save(curso);
	}

	@Override
	public void deleteById(Long id) {
		this.cursoRespository.deleteById(id);
		
	}

	@Override
	public Optional<Curso> update(Curso curso, Long id) {
		Optional<Curso> oc = Optional.empty();
		
			 oc = this.cursoRespository.findById(id);
			 if (oc.isPresent())
			 {
				 Curso curso_leido = oc.get();
				 BeanUtils.copyProperties(curso, curso_leido, "id");
				 oc = Optional.of(curso_leido);
			 }
			 
		
		return oc;
	}

	@Override
	public Optional<Curso> asignarAlumnos(List<Alumno> alumnos, Long id) {
		Optional<Curso> oc = Optional.empty();
		
			oc = this.cursoRespository.findById(id);
			if (oc.isPresent())
			{
				Curso curso_leido = oc.get();
				alumnos.forEach(a-> curso_leido.addAlumno(a));
				oc = Optional.of(curso_leido);
			}
		
		return oc;
	}

	@Override
	public Optional<Curso> eliminarAlumno(Alumno alumno, Long id) {
		Optional<Curso> oc = Optional.empty();
		
			oc = this.cursoRespository.findById(id);
			if (oc.isPresent())
			{
				Curso curso_leido = oc.get();
				curso_leido.eliminarAlumno(alumno);
				oc = Optional.of(curso_leido);
			}
		
		return oc;
	}
}
