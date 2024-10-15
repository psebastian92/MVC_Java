<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro Exitoso</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos-exito.css"> 
</head>
<body>
    <div class="success-container">
        <h1>Registro Exitoso</h1>
        <p>Los datos se han registrado correctamente.</p>
        <button onclick="window.location.href='PaginaCrearDatos.jsp';" class="back-button">Volver al Inicio</button>
    </div>
</body>
</html>
