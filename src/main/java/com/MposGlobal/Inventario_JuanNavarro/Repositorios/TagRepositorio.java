package com.MposGlobal.Inventario_JuanNavarro.Repositorios;


import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepositorio extends JpaRepository<Tag, Integer> {

    public Tag findByNombreIgnoreCase(String nombre);
}
