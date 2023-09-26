package edu.cta.academy.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import javax.persistence.ParameterMode;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

@Entity//ESTA CLASE ESTÁ ASOCIADA UNA TABLA DE LA BD
@Table(name = "alumnos")//esta asociada a esta
@NamedStoredProcedureQueries (
		{
			@NamedStoredProcedureQuery(name="Alumno.alumnosRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", resultClasses = edu.cta.academy.repository.entity.Alumno.class),
			@NamedStoredProcedureQuery(name="Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = {
					@StoredProcedureParameter (mode = ParameterMode.INOUT , type = Integer.class , name ="edadmax"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmin"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadmedia")
			})
		}
		)
public class Alumno {
	
	//TODO completar la definición de la entidad
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//usar el Autoinc de Mysql
	private Long id;
	
	@Size(min = 3, max = 20)
	private String nombre;
	
	@NotEmpty //el apellido debe tener longitud > 1
	@NotBlank //no admite que sea blancos
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(130)
	private int edad;
	
	@Column(name = "creado_en")
	private LocalDateTime creadoEn;
	
	@PrePersist //antes que se inserte un alumno, se ejecuta este método
	private void genererFechaCreacion()
	{
		this.creadoEn = LocalDateTime.now();//la fecha/hora actual
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}

	public Alumno(Long id, String nombre, String apellido, String email, int edad, LocalDateTime creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}

	public Alumno() {
		super();
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}
	
	
	
	

}
