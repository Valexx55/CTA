package edu.cta.academy.util;

import com.google.gson.Gson;

/**
 * Clase auxiliar con métodos para la ejecución de test
 */
public class UtilTest {

	public static String toJSON (Object object)
	{
		String json_dev = null;
		
			Gson gson = new Gson (); 
			json_dev = gson.toJson(object);//serializo - de Objeto a JSON-
				
				
		return json_dev;
	}
	
	public static <T>Object toObject(String json_string, Class<T> clase) {
		Object odev = null;
		
			Gson gson = new Gson ();
			odev = gson.fromJson(json_string, clase);//deserializo - de JSON a objeto
		
		return odev;
		
	}
}
