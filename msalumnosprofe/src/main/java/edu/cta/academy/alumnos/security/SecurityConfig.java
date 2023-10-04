package edu.cta.academy.alumnos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Esta clase configuramos la seguridad de con Spring Security
 */

@Configuration //Al arrancar, Spring tiene en cuenta esta clase y configuramos el contexto
public class SecurityConfig {
	
	
	@Bean //Hago que el Contexto, tenga un Codificador para las contraseñas
	public PasswordEncoder passwordEncoder ()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean //En este método, condiguramos el objeto que va a autenticar
	public AuthenticationManager authenticationManager()
	{
		return null;
	}
	
	@Bean //este bean es "la fuente" de los usuarios registrados
	public UserDetailsService userDetailsService ()
	{
		return null;
	}

}
