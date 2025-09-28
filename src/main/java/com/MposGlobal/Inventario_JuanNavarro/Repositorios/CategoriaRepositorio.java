package com.MposGlobal.Inventario_JuanNavarro.Repositorios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    Categoria findByNombreIgnoreCase(String nombre);
    Categoria findById(int id);
}
