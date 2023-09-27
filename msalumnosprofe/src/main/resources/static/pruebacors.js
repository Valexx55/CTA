console.log("pagina cargada ...");
fetch("http://localhost:8081/alumno")
.then(respuesta => respuesta.json())
.then (lista_alumnos => {
	console.log ('Aquí la vuelta con JS Normal');
	lista_alumnos.forEach (alumno => {
		console.log (alumno.id + " " +alumno.nombre);
	});
})
.catch(function(error) {
  console.log ('Aquí la vuelta con JS Normal');
  console.log('Hubo un problema con la petición Fetch:' + error.message);
});