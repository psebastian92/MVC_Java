package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ConexionBD;

public class MetodosCRUD implements InterfazDatosPersona {

    @Override
    public void guardar(DatosPersona persona) throws SQLException {
        String sql = "INSERT INTO datospersona (nombre, apellido, edad) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, persona.getNombre());
            statement.setString(2, persona.getApellido());
            statement.setInt(3, persona.getEdad());
            statement.executeUpdate();
        }
    }

    @Override
    public void actualizar(DatosPersona persona) throws SQLException {
        String sql = "UPDATE datospersona SET nombre=?, apellido=?, edad=? WHERE id=?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, persona.getNombre());
            statement.setString(2, persona.getApellido());
            statement.setInt(3, persona.getEdad());
            statement.setInt(4, persona.getId()); // Suponiendo que hay un campo 'id' en la tabla
            statement.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM datospersona WHERE id=?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public DatosPersona obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM datospersona WHERE id=?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    DatosPersona persona = new DatosPersona();
                    persona.setId(rs.getInt("id"));  // Asegúrate de tener el campo 'id' en la tabla
                    persona.setNombre(rs.getString("nombre"));
                    persona.setApellido(rs.getString("apellido"));
                    persona.setEdad(rs.getInt("edad"));
                    return persona;
                }
            }
        }
        return null;
    }

    @Override
    public List<DatosPersona> listarTodos() throws SQLException {
        List<DatosPersona> personas = new ArrayList<>();
        String sql = "SELECT * FROM datospersona";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                DatosPersona persona = new DatosPersona();
                persona.setId(rs.getInt("id"));  // Suponiendo que hay un campo 'id' en la tabla
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
                personas.add(persona);
            }
        }
        return personas;
    }

    // Nuevo método para paginación
    public List<DatosPersona> listarPaginado(int limite, int offset) throws SQLException {
        List<DatosPersona> personas = new ArrayList<>();
        String sql = "SELECT * FROM datospersona LIMIT ? OFFSET ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, limite);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    DatosPersona persona = new DatosPersona();
                    persona.setId(rs.getInt("id"));  // Suponiendo que hay un campo 'id' en la tabla
                    persona.setNombre(rs.getString("nombre"));
                    persona.setApellido(rs.getString("apellido"));
                    persona.setEdad(rs.getInt("edad"));
                    personas.add(persona);
                }
            }
        }
        return personas;
    }

    // Nuevo método para buscar por nombre o apellido
    public List<DatosPersona> buscarPorNombreOApellido(String busqueda, int limite, int offset) throws SQLException {
        List<DatosPersona> personas = new ArrayList<>();
        String sql = "SELECT * FROM datospersona WHERE nombre LIKE ? OR apellido LIKE ? LIMIT ? OFFSET ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, "%" + busqueda + "%");
            statement.setString(2, "%" + busqueda + "%");
            statement.setInt(3, limite);
            statement.setInt(4, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    DatosPersona persona = new DatosPersona();
                    persona.setId(rs.getInt("id"));  // Suponiendo que hay un campo 'id' en la tabla
                    persona.setNombre(rs.getString("nombre"));
                    persona.setApellido(rs.getString("apellido"));
                    persona.setEdad(rs.getInt("edad"));
                    personas.add(persona);
                }
            }
        }
        return personas;
    }

    // Nuevo método para contar registros
    public int contarRegistros(String busqueda) throws SQLException {
        String sql = busqueda == null || busqueda.trim().isEmpty() ?
                "SELECT COUNT(*) AS total FROM datospersona" :
                "SELECT COUNT(*) AS total FROM datospersona WHERE nombre LIKE ? OR apellido LIKE ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                statement.setString(1, "%" + busqueda + "%");
                statement.setString(2, "%" + busqueda + "%");
            }
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }
}
