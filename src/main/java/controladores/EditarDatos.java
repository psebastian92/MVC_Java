package controladores;

import java.io.IOException;
import java.sql.SQLException;

import modelo.DatosPersona;
import modelo.InterfazDatosPersona;
import modelo.MetodosCRUD;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditarDatos")
public class EditarDatos extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private InterfazDatosPersona personaDAO = new MetodosCRUD(); // Instancia del DAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        // Validar que se haya pasado el id
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp?error=missing_id");
            return;
        }

        int id = Integer.parseInt(idStr);
        try {
            DatosPersona persona = personaDAO.obtenerPorId(id); // Cambiado a obtenerPorId
            if (persona != null) {
                // Pasar los datos como atributos a la página JSP
                request.setAttribute("id", persona.getId());
                request.setAttribute("nombre", persona.getNombre());
                request.setAttribute("apellido", persona.getApellido());
                request.setAttribute("edad", persona.getEdad());
                request.getRequestDispatcher("vistas/PaginaEditarDatos.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/vistas/error.jsp?error=no_such_record");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp?error=database_error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener parámetros del formulario
        String idStr = request.getParameter("idDatos");
        String nombre = request.getParameter("nombreDatos");
        String apellido = request.getParameter("apellidoDatos");
        String edadStr = request.getParameter("edadDatos");

        // Validar parámetros
        if (idStr == null || idStr.isEmpty() || nombre == null || nombre.trim().isEmpty()
                || apellido == null || apellido.trim().isEmpty() || edadStr == null || edadStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
            return;
        }

        // Convertir edad a un entero
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorEdad", "La edad debe ser un número entero.");
            request.setAttribute("nombre", nombre);
            request.setAttribute("apellido", apellido);
            request.setAttribute("edad", edadStr);
            request.setAttribute("id", idStr);
            request.getRequestDispatcher("/vistas/PaginaEditarDatos.jsp").forward(request, response);
            return;
        }

        // Crear objeto DatosPersona para actualizar
        DatosPersona persona = new DatosPersona();
        persona.setId(Integer.parseInt(idStr));
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setEdad(edad);

        // Actualizar datos en la base de datos
        try {
            personaDAO.actualizar(persona); // Cambiado a actualizar
            request.getRequestDispatcher("/LeerDatos").forward(request, response); // Redirige de éxito
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp?error=database_error");
        }
    }
}
