<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario de Individuos</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/estilos-crear.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<!-- Agrega esta línea -->
</head>

<body>
	<div class="form-container">
		<h1 class="form-title">Agregar Nueva Persona</h1>

		<form action="${pageContext.request.contextPath}/AgregarDatos"
			method="post" class="data-form">
			<div class="form-group">
				<label for="nombreDatos" class="form-label">Nombre de la
					persona:</label> <input type="text" id="nombreDatos" name="nombreDatos"
					required class="form-input">
			</div>
			<div class="form-group">
				<label for="apellidoDatos" class="form-label">Apellido de la
					persona:</label> <input type="text" id="apellidoDatos" name="apellidoDatos"
					required class="form-input">
			</div>
			<div class="form-group">
				<label for="edadDatos" class="form-label">Edad de la
					persona:</label> <input type="number" id="edadDatos" name="edadDatos"
					required class="form-input">
			</div>

			<!-- Mostrar mensaje de error con la edad (si existe) -->
			<%
			String errorEnLaEdad = (String) request.getAttribute("errorEdad");
			if (errorEnLaEdad != null) {
			%>
			<div id="mensaje-error" class="mensaje-error">
			<i class="fas fa-exclamation-circle" style="color: #dc3545; margin-right: 5px;"></i>
				<!-- Ícono de error rojo -->
				<strong><%=errorEnLaEdad%></strong>
			</div>
			<%
			}
			%>

			<!-- Mostrar mensaje de éxito> -->
			<%
			String mensajeExito = (String) request.getAttribute("mensajeExito");
			if (mensajeExito != null) {
			%>

			<div id="mensaje-exito" class="mensaje-exito">
				<i class="fas fa-check-circle"
					style="color: #28a745; margin-right: 5px;"></i>
				<!-- Ícono de tilde verde -->
				<strong><%=mensajeExito%></strong>
			</div>
			<%
			}
			%>
			<div class="button-container">
				<input type="submit" value="Agregar" class="submit-button">
			</div>

			<div class="button-container">
				<a href="${pageContext.request.contextPath}/LeerDatos"
					class="submit-button link-button">Ver Personas</a>
			</div>
		</form>

	</div>


	<!-- JavaScript para ocultar el mensaje de error después de 10 segundos -->
	<script>
		window.onload = function() {
			// Si el mensaje de error está presente
			var errorMessage = document.getElementById("mensaje-error");
			var succesMessage = document.getElementById("mensaje-exito");
			if (errorMessage) {
				// Desaparecer el mensaje después de 5 segundos (5000 milisegundos)
				setTimeout(function() {
					errorMessage.style.display = 'none';
				}, 5000);
			} else {
				setTimeout(function() {
					succesMessage.style.display = 'none';
				}, 5000);
			}
		};
	</script>
</body>
</html>
