package edu.cta.academy.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity//ESTA CLASE ESTÁ ASOCIADA UNA TABLA DE LA BD
@Table(name = "alumnos")//esta asociada a esta
public class Alumno {
	
	//TODO completar la definición de la entidad
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//usar el Autoinc de Mysql
	private Long id;
	
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	
	@Column(name = "creado_en")
	private LocalDateTime creadoEn;
	
	@PrePersist //antes que se inserte un alumno, se ejecuta este método
	private void genererFechaCreacion()
	{
		this.creadoEn = LocalDateTime.now();//la fecha/hora actual
	}
	
	

}
