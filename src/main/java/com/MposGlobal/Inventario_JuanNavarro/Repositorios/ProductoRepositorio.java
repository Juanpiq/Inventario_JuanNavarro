package com.MposGlobal.Inventario_JuanNavarro.Repositorios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    public Producto findByNombreIgnoreCase(String nombre);

    public Producto findByIdProducto(int id);
}
