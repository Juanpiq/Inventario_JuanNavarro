package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Categoria;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;

import java.util.List;

public interface CategoriaInterfazServicio {

    List<Categoria> listaCategorias();

    Categoria buscar(int id);

    Categoria buscar(String nombre);

    List<Producto> productosCategoria(int id);

    List<Producto> productosCategoria(String nombre);

    Categoria desactivarCategoria(int id);

    Categoria desactivarCategoria(String nombre);

    Categoria activarCategoria(int id);

    Categoria activarCategoria(String nombre);

    Categoria modificarCategoria(Categoria categoria);
}
