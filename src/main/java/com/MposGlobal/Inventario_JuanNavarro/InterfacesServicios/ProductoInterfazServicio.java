package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;

import java.util.List;

public interface ProductoInterfazServicio {

    List<Producto> listaProductos();

    Producto buscarProducto(int id);

    Producto buscarProducto(String nombre);


}
