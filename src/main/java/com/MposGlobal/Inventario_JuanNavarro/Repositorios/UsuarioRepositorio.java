package com.MposGlobal.Inventario_JuanNavarro.Repositorios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    public Usuario findByNombreUsuario(String nombreUsuario);
}
