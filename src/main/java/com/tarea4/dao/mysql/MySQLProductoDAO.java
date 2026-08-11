package com.tarea4.dao.mysql;

import com.tarea4.dao.ProductoDAO;
import com.tarea4.database.DatabaseConnection;
import com.tarea4.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/* Implementación MySQL del ProductoDAO. */
public class MySQLProductoDAO implements ProductoDAO {

    private static final String TABLA_PRODUCTOS = "productos_20252437";

    private Producto crearProducto(ResultSet result) throws Exception {
        Producto producto = new Producto();
        producto.setId(result.getInt("id"));
        producto.setNombre(result.getString("nombre"));
        producto.setMarca(result.getString("marca"));
        producto.setCategoria(result.getString("categoria"));
        producto.setPrecio(result.getDouble("precio"));
        producto.setCantidadDisponible(result.getInt("cantidad_disponible"));
        return producto;
    }

    @Override
    public List<Producto> listarProductos() throws Exception {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA_PRODUCTOS + " ORDER BY id";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) productos.add(crearProducto(result));
        }
        return productos;
    }

    @Override
    public void registrar(Producto producto) throws Exception {
        String sql = "INSERT INTO " + TABLA_PRODUCTOS
                + "(nombre, marca, categoria, precio, cantidad_disponible) VALUES(?,?,?,?,?)";
        guardar(sql, producto, false);
    }

    @Override
    public void actualizar(Producto producto) throws Exception {
        String sql = "UPDATE " + TABLA_PRODUCTOS
                + " SET nombre=?, marca=?, categoria=?, precio=?, cantidad_disponible=? WHERE id=?";
        guardar(sql, producto, true);
    }

    private void guardar(String sql, Producto producto, boolean actualizar) throws Exception {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getMarca());
            statement.setString(3, producto.getCategoria());
            statement.setDouble(4, producto.getPrecio());
            statement.setInt(5, producto.getCantidadDisponible());
            if (actualizar) statement.setInt(6, producto.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM " + TABLA_PRODUCTOS + " WHERE id=?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
