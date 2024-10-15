<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lista de Personas</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/estilos-leerdatos.css">
<!-- Ruta al CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
	<div class="container">
		<h1 class="title">Lista de Personas</h1>
		<form method="GET"
			action="${pageContext.request.contextPath}/LeerDatos"
			class="search-form">
			<div class="search-container">
				<input type="text" class="search-input"
					placeholder="Buscar por nombre o apellido" name="busqueda">
				<button class="search-button" type="submit">Buscar</button>
			</div>
		</form>
		<table class="table">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Edad</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<%
				ResultSet conjuntoResultados = (ResultSet) request.getAttribute("conjuntoResultados");
				boolean hayResultados = false; // Variable para verificar si hay resultados
				if (conjuntoResultados != null) {
					while (conjuntoResultados.next()) {
						hayResultados = true; // Hay al menos un resultado
						int id = conjuntoResultados.getInt("id"); // Se usa internamente, no en el HTML visible
						String nombre = conjuntoResultados.getString("nombre");
						String apellido = conjuntoResultados.getString("apellido");
						int edad = conjuntoResultados.getInt("edad");
				%>
				<tr>
					<td><%=nombre%></td>
					<td><%=apellido%></td>
					<td><%=edad%></td>
					<td>
						<div class="action-container">
							<!-- Formulario para editar -->
							<form action="<%=request.getContextPath()%>/EditarDatos"
								method="GET" style="display: inline;">
								<input type="hidden" name="id" value="<%=id%>">
								<button type="submit" class="action-button edit-button">
									<i class="fas fa-edit"></i>
								</button>
							</form>
							<!-- Formulario para eliminar -->
							<form action="<%=request.getContextPath()%>/EliminarDatos"
								method="post" style="display: inline;">
								<input type="hidden" name="id" value="<%=id%>">
								<button type="submit" class="action-button delete-button"
									onclick="return confirm('¿Estás seguro de que deseas eliminar este registro?');">
									<i class="fas fa-trash"></i>
								</button>
							</form>
						</div>
					</td>
				</tr>
				<%
				}
				}

				if (!hayResultados) { // Si no hay resultados
				%>
				<tr>
					<td colspan="4" class="no-results" >No hay resultados disponibles.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<!-- Paginación y botones de regreso -->
		<div class="pagination">
			<%
			Integer paginaActual = (Integer) request.getAttribute("paginaActual");
			Integer totalRegistros = (Integer) request.getAttribute("totalRegistros");
			Integer datosPorPagina = (Integer) request.getAttribute("REGISTROS_POR_PAGINA");
			boolean hayMasDatos = totalRegistros > paginaActual * datosPorPagina;
			if (paginaActual == null) {
				paginaActual = 1; // o cualquier valor por defecto que desees
			}

			if (totalRegistros == null) {
				totalRegistros = 0; // o cualquier valor por defecto que desees
			}
			%>
			<a
				href="${pageContext.request.contextPath}/LeerDatos?pagina=<%= paginaActual - 1 %>"
				class="pagination-button <%= paginaActual == 1 ? "disabled" : "" %>">Anterior</a>
			<a
				href="${pageContext.request.contextPath}/LeerDatos?pagina=<%= paginaActual + 1 %>"
				class="pagination-button <%= !hayMasDatos ? "disabled" : "" %>">Siguiente</a>
		</div>

		<div class="button-search-container">
			<a
				href="${pageContext.request.contextPath}/vistas/PaginaCrearDatos.jsp"
				class="back-button"> <i class="fas fa-undo"></i></a>
		</div>
	</div>
</body>
</html>
