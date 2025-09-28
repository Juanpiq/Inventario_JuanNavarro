package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO (
        @NotBlank(message = "nombre de usuario obligatorio")
        String nombreUsuario,

        @NotBlank(message = "clave obligatoria")
        String clave
){
}
