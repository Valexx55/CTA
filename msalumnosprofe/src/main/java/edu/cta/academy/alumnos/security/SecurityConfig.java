package edu.cta.academy.alumnos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Esta clase configuramos la seguridad de con Spring Security
 */

@EnableWebSecurity//activamos módulo de seguridad
@Configuration //Al arrancar, Spring tiene en cuenta esta clase y configuramos el contexto
public class SecurityConfig {
	
	
	@Bean //Hago que el Contexto, tenga un Codificador para las contraseñas
	public PasswordEncoder passwordEncoder ()
	{
		
		return new BCryptPasswordEncoder();
		//BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		//System.out.println( bc.encode("admin"));//contraseña cifrada
	}
	
	@Bean //En este método, condiguramos el objeto que va a autenticar
	public AuthenticationManager authenticationManager
			(
					UserDetailsService userDetailsService,
					PasswordEncoder pe,
					HttpSecurity httpSecurity//configuramos la seguridad de nuestra app
					
			) throws Exception
	{
		
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(pe).and().parentAuthenticationManager(null)// no use la cadena de Autenticadores
				.build();
	}
	
	@Bean //este bean es "la fuente" de los usuarios registrados
	public UserDetailsService userDetailsService (PasswordEncoder pe)//este objeto pe, es el de la línea 21
	{
		InMemoryUserDetailsManager manager = null;
		
			//definimos las crendeciales de los usuarios de nuestra app/servicio
			manager = new InMemoryUserDetailsManager();
			manager.createUser(User.withUsername("admin").password(pe.encode("zaragoza")).roles().build());
		
		return manager;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity httpSecurity, AuthenticationManager am) throws Exception
	{
		return httpSecurity
				//.authenticationManager(am)
				.csrf().disable()//SIN ESTADO/SESIÓN, SIN COOKIES, 
				.cors().disable()
				.authorizeHttpRequests((authorize) -> authorize
				        .anyRequest().authenticated()
				    )
				.httpBasic()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.build();
	}
	
	

}
