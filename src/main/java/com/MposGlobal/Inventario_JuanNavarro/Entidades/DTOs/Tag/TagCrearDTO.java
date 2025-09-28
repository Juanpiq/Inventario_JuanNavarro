package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagCrearDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 75, message = "El nombre no puede superar 100 caracteres")
        String nombre
) {
}
