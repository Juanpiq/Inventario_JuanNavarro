package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;

import java.util.List;

public interface ProductoInterfazServicio {

    List<ProductoRespDTO> listarProductos();

    ProductoRespDTO crearProducto(ProductoCrearDTO productoCrearDTO) throws BusinessException;

    ProductoRespDTO buscarProducto(int id) throws BusinessException;

    ProductoRespDTO buscarProducto(String nombre) throws BusinessException;

    String desactivarProducto(String nombre) throws BusinessException;

    String desactivarProducto(int id) throws BusinessException;

    String activarProducto(String nombre) throws BusinessException;

    String activarProducto(int id) throws BusinessException;

    String modificarProducto(ProductoActDTO productoActDTO) throws BusinessException;

}
