<%-- 
    Document   : ListarEstudiantes
    Created on : 18/09/2017, 09:55:03 PM
    Author     : willy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Listar Estudiantes</title>
<script src="http://code.jquery.com/jquery-latest.js">

</script>
<script>
	$(document).ready(function() {
		$('#submit').click(function(event) {
			var cursoVar = $('#curso').val();
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			$.get('ServletEstudiantes', {
				curso: cursoVar
			}, function(responseText) {
				$('#tabla').html(responseText);
			});
		});
	});
</script>
</head>
<body>
	<h2>   Listar Estudiantes</h2>
	<form id="form1">
                CURSO: <input type="number" id="curso" /> <br>
		<input type="button" id="submit" value="Buscar" /> 
	</form>
	<br>
	<!-- 	En este div metemos el contenido de la tabla con AJAX -->
	<div id="tabla"></div>
    </body>
</html>
