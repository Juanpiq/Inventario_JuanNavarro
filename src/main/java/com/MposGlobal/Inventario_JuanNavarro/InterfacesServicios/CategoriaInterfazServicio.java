package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Categoria;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;

import java.util.List;

public interface CategoriaInterfazServicio {

    List<CategoriaRespDTO> listarCategorias();

    CategoriaRespDTO crearCategoria(CategoriaCrearDTO catdto) throws BusinessException;

    CategoriaRespDTO buscarCategoria(int id) throws BusinessException;

    CategoriaRespDTO buscarCategoria(String nombre) throws BusinessException;

    List<ProductoRespDTO> listarProductosCategoria(int id) throws BusinessException;

    String desactivarCategoria(int id) throws BusinessException;

    String desactivarCategoria(String nombre) throws BusinessException;

    String activarCategoria(int id) throws BusinessException;

    String activarCategoria(String nombre) throws BusinessException;

    String modificarCategoria(CategoriaActDTO categoria) throws BusinessException;
}
