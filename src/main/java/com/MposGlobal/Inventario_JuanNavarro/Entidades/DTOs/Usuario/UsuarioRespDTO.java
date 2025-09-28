package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario;

import java.time.LocalDateTime;

public record UsuarioRespDTO(
        int id,
        String nombreCompleto,
        String nombreUsuario,
        String nivelAcceso,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaUltimoIngreso
) {
}
