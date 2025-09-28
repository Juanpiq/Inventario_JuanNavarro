package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioCrearDTO(

        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 100, message = "El nombre completo no puede superar los 100 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 50, message = "El nombre de usuario no puede superar los 50 caracteres")
        String nombreUsuario,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
        String clave,

        @NotBlank(message = "El nivel de acceso es obligatorio")
        @Pattern(regexp = "(?i)ADMIN|USER", message = "El nivel de acceso debe ser ADMIN o USER")
        String nivelAcceso
) {
}
