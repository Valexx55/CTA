package edu.cta.academy.alumnos.cliente;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.cta.academy.comun.entity.Curso;

@FeignClient(name = "mscursos") //indico el id/nombre del microservicio que voy a consumir
public interface CursoFeignClient {
	
	@GetMapping("/curso/obtenerCursoAlumno/{idalumno}")
	public Optional<Curso> obtenerCursoAlumno(@PathVariable Long idalumno);

}
