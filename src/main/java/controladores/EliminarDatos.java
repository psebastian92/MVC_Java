package controladores;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.MetodosCRUD;

@WebServlet("/EliminarDatos")
public class EliminarDatos extends HttpServlet {
    private MetodosCRUD metodosCRUD = new MetodosCRUD();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);

            // Eliminar el registro usando el DAO
            try {
                metodosCRUD.eliminar(id);
                // Redirigir a LeerDatos después de eliminar
                response.sendRedirect(request.getContextPath() + "/LeerDatos");
            } catch (SQLException e) {
                e.printStackTrace();
                // Redirigir a la página de error
                response.sendRedirect(request.getContextPath() + "/vistas/error.jsp");
            }
        } else {
            // Redirigir a la página de error si el id no es válido
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp");
        }
    }
}
