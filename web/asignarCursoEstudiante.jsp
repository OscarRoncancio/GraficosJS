<%-- 
    Document   : asignarCursoEstudiante
    Created on : 21/09/2017, 01:04:42 AM
    Author     : willy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Listar Cursos</title>
<script src="http://code.jquery.com/jquery-latest.js">

</script>
<script>
	$(document).ready(function() {
                      
		$('#submit').click(
                          function(event) {
			$.get('ServletEstudiantes', {
			}, function(responseText) {
				$('#tabla').html(responseText);
			});
                        function(event) {
			$.post('ServletEstudiantes', {
			}, function(responseText) {
				$('#respuesta').html(responseText);
			});
		});
	});
</script>
</head>
<body>
	<h2>   Listar Estudiantes</h2>
	<form id="form1">
                ID ESTUDIANTE: <input type="number" id="id" /> <br>
                CURSO: <input type="number" id="curso" /> <br>
                <input type="hidden" id="hidden" value="2"/> 
		<input type="button" id="submit" value="Buscar" /> 
	</form>
	<br>
	<!-- 	En este div metemos el contenido de la tabla con AJAX -->
	<div id="tabla"></div>
        <div id="respuesta"></div>
        
    </body>   
</html>
