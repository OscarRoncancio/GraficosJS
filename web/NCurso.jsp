<%-- 
    Document   : NCurso
    Created on : 7/09/2017, 12:14:35 AM
    Author     : willy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head> 
       <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Cursos</title>
     <script src="http://code.jquery.com/jquery-latest.js">

     </script>
<script>
	$(document).ready(function() {
		$('#submit').click(function(event) {
                        var idVar = $('#id').val();
			var nombreVar = $('#nombre').val();
			var profesorVar = $('#profesor').val();
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			$.post('ServletCursos', {
                                id:idVar,
				nombre : nombreVar,
				profesor: profesorVar
			}, function(responseText) {
				$('#tabla').html(responseText);
			});
		});
	});
</script>
</head>
<body>
	<h2>Cursos</h2>
	<form id="form1">
                Id: <input type="number" id="id" /> <br>
		Nombre:<input type="text" id="nombre" /> <br>
                Profesor: <input type="number" id="profesor" /> <br>
		<input type="button" id="submit" value="Añadir" /> 
	</form>
	<br>
	<!-- 	En este div metemos el contenido de la tabla con AJAX -->
	<div id="tabla"></div>
    </body>
</html>
