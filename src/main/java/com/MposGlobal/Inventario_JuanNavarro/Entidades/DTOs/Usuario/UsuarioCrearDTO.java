package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Rol;

public record UsuarioCrearDTO(
        String nombreCompleto,
        String nombreUsuario,
        String clave,
        Rol nivelAcceso
) {
}
