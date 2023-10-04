package edu.cta.academy.alumnos.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component//por estar en Spring (Boot)
@WebFilter(urlPatterns = "/alumno") //cualquier peticion al AlumnoController, pasará por aquí antes
public class MiFiltro implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("A LA IDA");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String cadena_auth =  httpServletRequest.getHeader("Authorization");
		System.out.println("Cadena Auth = " + cadena_auth);
		String clave_auth = cadena_auth.split(" ")[1];
		byte[] clave_deco = Base64.getDecoder().decode(clave_auth);
		String clave = new String(clave_deco, StandardCharsets.UTF_8);
		System.out.println( "CLAVE DECO = " + clave);
		if (clave.equals("admin:zaragoza"))
		{
			chain.doFilter(request, response);//le dejamos pasar
		} else 
		{
			System.out.println("Clave incorrecta");
			HttpServletResponse respuesta =  (HttpServletResponse) response;
			//respuesta.setStatus(401);
			respuesta.sendError(401, "NO PUEDES PASAR PECADOR No eres el Rey De Aragón");
		}
			
		//SI EL USUARIO ES admin:zaragoza le dejo pasar
		//admin:zaragoza
		//si no, le decimos que no
		//Basic YWRtaW46emFyYWdvemE=
		
		System.out.println("A LA VUELTA");
	}

}
