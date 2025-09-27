package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario;

public record UsuarioActDTO(
        int id,
        String nombreCompleto,
        String clave,
        boolean activo,
        String nivelAcceso
) {
}
