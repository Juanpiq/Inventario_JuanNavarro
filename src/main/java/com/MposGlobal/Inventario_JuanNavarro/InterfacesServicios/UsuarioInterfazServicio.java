package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Usuario;

import java.util.List;

public interface UsuarioInterfazServicio {

    List<Usuario> listaUsuarios();

    Usuario crearUsuario(Usuario usuario);

    Usuario buscarUsuario(int id);

    Usuario buscarUsuario(String nombre);

    Usuario modificarUsuario(Usuario usuario);
}
