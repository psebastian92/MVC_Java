<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Persona</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/estilos-editar.css">
<!-- Ruta al CSS -->
</head>
<body>
	<div class="form-container">
		<h1 class="form-title">Editar Persona</h1>

		<!-- Formulario para editar persona -->
		<form action="${pageContext.request.contextPath}/EditarDatos"
			method="post" class="data-form">
			<!-- Campo oculto para enviar el ID de la persona -->
			<input type="hidden" name="idDatos" value="${id}" />

			<div class="form-group">
				<label for="nombreDatos" class="form-label">Nombre de la
					persona:</label> <input type="text" id="nombreDatos" name="nombreDatos"
					value="${nombre}" required class="form-input">
			</div>
			<div class="form-group">
				<label for="apellidoDatos" class="form-label">Apellido de la
					persona:</label> <input type="text" id="apellidoDatos" name="apellidoDatos"
					value="${apellido}" required class="form-input">
			</div>
			<div class="form-group">
				<label for="edadDatos" class="form-label">Edad de la
					persona:</label> <input type="text" id="edadDatos" name="edadDatos"
					value="${edad}" required class="form-input">
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

			<!-- Botón para enviar el formulario y guardar los cambios -->
			<div class="button-container">
				<input type="submit" value="Guardar Cambios" class="submit-button">
			</div>

			<!-- Botón para regresar a la lista de personas -->
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
