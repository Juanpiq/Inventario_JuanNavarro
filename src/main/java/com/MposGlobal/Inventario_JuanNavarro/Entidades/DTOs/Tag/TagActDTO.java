package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TagActDTO(
        @NotNull(message = "El ID es obligatorio")
        Integer idTag,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 75, message = "El nombre no puede superar 100 caracteres")
        String nombre
) {
}
