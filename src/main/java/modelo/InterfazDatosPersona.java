package modelo;

import java.sql.SQLException;
import java.util.List;

public interface InterfazDatosPersona {
    void guardar(DatosPersona persona) throws SQLException;
    void actualizar(DatosPersona persona) throws SQLException;
    void eliminar(int id) throws SQLException;
    DatosPersona obtenerPorId(int id) throws SQLException;
    List<DatosPersona> listarTodos() throws SQLException;
}
