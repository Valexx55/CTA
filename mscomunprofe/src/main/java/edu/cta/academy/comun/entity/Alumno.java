package edu.cta.academy.comun.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ParameterMode;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

@Entity//ESTA CLASE ESTÁ ASOCIADA UNA TABLA DE LA BD
@Table(name = "alumnos")//esta asociada a esta
@NamedStoredProcedureQueries (
		{
			@NamedStoredProcedureQuery(name="Alumno.alumnosRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", resultClasses = edu.cta.academy.comun.entity.Alumno.class),
			@NamedStoredProcedureQuery(name="Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = {
					@StoredProcedureParameter (mode = ParameterMode.INOUT , type = Integer.class , name ="edadmax"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmin"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadmedia")
			})
		}
		)
public class Alumno extends RepresentationModel<Alumno> {
	
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
	
	@Lob //objeto binario largo
	@JsonIgnore //evito serializar este atributo a JSON
	private byte[] foto;
	
	@Column(name = "creado_en")
	private LocalDateTime creadoEn;
	
	/**
	 * este método es usado a modo de "flag" - bandera-indicador
	 * si un alumno tiene foto asociada, valdrá un númer
	 * si no, será null
	 * @return
	 */
	public Integer getFotoHashCode ()
	{
		Integer idev = null;
		
			if (this.foto!=null)
			{
				idev = this.foto.hashCode();
			}
		
		
		return idev;
	}
	
	
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
	

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
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


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		boolean iguales = false;
		
			if (this==obj)
			{
				iguales = true;
				//pattern-matching java14 SI SE CUMPLE EL instanceOf, automáticamente, hace el casting de Object a Alumno en a
			} else if (obj instanceof Alumno a) 
			{
				//iguales = this.id.equals(a.id);
				iguales = Objects.equals(this.id, a.id);
			}
		return iguales;
	}
	
	

	

}
