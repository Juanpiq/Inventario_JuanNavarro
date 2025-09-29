package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaActDTO(
        @Min(value = 1, message = "El ID de categoría es obligatorio y debe ser mayor que 0")
        int id,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre
) {
}
