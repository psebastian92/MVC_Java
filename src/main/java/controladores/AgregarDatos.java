package controladores;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import modelo.DatosPersona;
import modelo.InterfazDatosPersona;
import modelo.MetodosCRUD;

@WebServlet("/AgregarDatos")
public class AgregarDatos extends HttpServlet {

    private InterfazDatosPersona datosPersonaDAO;

    @Override
    public void init() throws ServletException {
        datosPersonaDAO = new MetodosCRUD(); // Instancia de la clase DAO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombreDatos");
        String apellido = request.getParameter("apellidoDatos");
        String edadStr = request.getParameter("edadDatos");

        // Validar parámetros
        if (nombre == null || nombre.trim().isEmpty() || apellido == null || apellido.trim().isEmpty()
                || edadStr == null || edadStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        // Convertir edad a entero
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorEdad", "La edad debe ser un número entero.");
            request.getRequestDispatcher("/vistas/PaginaCrearDatos.jsp").forward(request, response);
            return;
        }

        // Crear objeto DatosPersona
        DatosPersona persona = new DatosPersona(nombre, apellido, edad);

        // Guardar datos usando el DAO
        try {
            datosPersonaDAO.guardar(persona);
            request.setAttribute("mensajeExito", "¡Datos guardados correctamente!");
            request.getRequestDispatcher("/vistas/PaginaCrearDatos.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp");
        }
    }
}
