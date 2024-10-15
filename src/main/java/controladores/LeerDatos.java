package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DatosPersona;
import modelo.MetodosCRUD;

@WebServlet("/LeerDatos")
public class LeerDatos extends HttpServlet {

    private MetodosCRUD metodosCRUD = new MetodosCRUD();
    private static final int REGISTROS_POR_PAGINA = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pagina = 1; // P�gina predeterminada
        String busqueda = request.getParameter("busqueda"); // Par�metro de b�squeda

        // Validar si el par�metro de p�gina existe
        if (request.getParameter("pagina") != null) {
            try {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            } catch (NumberFormatException e) {
                pagina = 1; // Si no es un n�mero v�lido, lo dejamos en 1
            }
        }

        try {
            List<DatosPersona> listaPersonas;
            int totalRegistros;

            // Si hay un criterio de b�squeda, utilizar la b�squeda paginada
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                listaPersonas = metodosCRUD.buscarPorNombreOApellido(busqueda, REGISTROS_POR_PAGINA, (pagina - 1) * REGISTROS_POR_PAGINA);
                totalRegistros = metodosCRUD.contarRegistros(busqueda);
            } else {
                // Si no hay b�squeda, utilizar el m�todo de listar todos paginado
                listaPersonas = metodosCRUD.listarPaginado(REGISTROS_POR_PAGINA, (pagina - 1) * REGISTROS_POR_PAGINA);
                totalRegistros = metodosCRUD.contarRegistros(null);
            }

            // Calcular el n�mero total de p�ginas
            int totalPaginas = (int) Math.ceil((double) totalRegistros / REGISTROS_POR_PAGINA);

            // Configurar los atributos para la vista JSP
            request.setAttribute("REGISTROS_POR_PAGINA", REGISTROS_POR_PAGINA);
            request.setAttribute("listaPersonas", listaPersonas);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalRegistros", totalRegistros);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("busqueda", busqueda); // Para que se mantenga el criterio de b�squeda en la vista

            // Redirigir a la vista JSP
            request.getRequestDispatcher("vistas/PaginaLeerDatos.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/vistas/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Reutilizar la l�gica de doGet
    }
}
