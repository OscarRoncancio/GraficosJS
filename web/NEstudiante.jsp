<%-- 
    Document   : index
    Created on : 23/08/2017, 12:49:40 PM
    Author     : willy
--%>

<%@page import="Modelo.Estudiante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AJAX JSP Servelts</title>
<script src="http://code.jquery.com/jquery-latest.js">

</script>
<script>
	$(document).ready(function() {
		$('#submit').click(function(event) {
                        var idVar = $('#id').val();
			var nombreVar = $('#nombre').val();
			var apellidoVar = $('#apellido').val();
			var cursoVar = $('#curso').val();
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			$.post('ServletEstudiantes', {
                                id:idVar,
				nombre : nombreVar,
				apellido: apellidoVar,
				curso: cursoVar
			}, function(responseText) {
				$('#tabla').html(responseText);
			});
		});
	});
</script>
</head>
<body>
	<h2>Estudiantes</h2>
	<form id="form1">
                ID: <input type="number" id="id" /> <br>
		Nombre:<input type="text" id="nombre" /> <br>
		Apellido: <input type="text" id="apellido" /> <br>
                CURSO: <input type="number" id="curso" /> <br>
		<input type="button" id="submit" value="Añadir" /> 
	</form>
	<br>
	<!-- 	En este div metemos el contenido de la tabla con AJAX -->
	<div id="tabla"></div>
    </body>
</html>
