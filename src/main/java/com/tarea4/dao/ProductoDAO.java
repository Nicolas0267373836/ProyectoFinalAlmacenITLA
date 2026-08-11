package com.tarea4.dao;

import com.tarea4.model.Producto;
import java.util.List;

/* Polimorfismo: las pantallas usan esta interfaz sin depender directamente del SQL. */
public interface ProductoDAO {

    List<Producto> listarProductos() throws Exception;
    void registrar(Producto producto) throws Exception;
    void actualizar(Producto producto) throws Exception;
    void eliminar(int id) throws Exception;
}
