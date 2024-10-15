<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Error en el Registro</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos-error.css"> <!-- Ajusta la ruta si es necesario -->
</head>
<body>
    <div class="error-container">
        <h1>Error en el Registro</h1>
        <p>Ha ocurrido un error al intentar registrar los datos. Por favor, intenta nuevamente.</p>
       
        <%
            String error = request.getParameter("error");
            if (error != null) {
                if (error.equals("edad")) {
        %>
                    <p> <strong style="color: red;">Detalles:</strong> La edad ingresada no es válida. Por favor, verifica que sea un número.</p>
        <%
                }
            
            }
        %>
        <button onclick="window.location.href='PaginaCrearDatos.jsp';" class="back-button">Volver al Inicio</button>
    </div>
</body>
</html>
