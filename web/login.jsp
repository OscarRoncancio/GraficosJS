<%-- 
    Document   : login
    Created on : 7/09/2017, 02:08:11 PM
    Author     : willy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login</title>
    <script src="http://code.jquery.com/jquery-latest.js">

</script>
<script>
	$(document).ready(function() {
		$('#submit').click(function(event) {
                        var usuarioVar = $('#usuario').val();
			var contVar = $('#contraseña').val();
			// Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
			$.post('ServletUsuarios', {
                                usuario:usuarioVar,
				contraseña: contVar
			}, function(responseText) {
				$('#tabla').html(responseText);
			});
		});
	});
</script>
</head>
<body>
	<h2>Login</h2>
	<form id="form1">
                Usuario: <input type="text" id="usuario" /> <br>
                Contraseña:<input type="password" id="contraseña" /> <br>
                    <input type="button" id="submit" value="Ingresar" /> 
	</form>
	<br>
	<!-- 	En este div metemos el contenido de la tabla con AJAX -->
	<div id="tabla"></div>
    </body>
</html>
