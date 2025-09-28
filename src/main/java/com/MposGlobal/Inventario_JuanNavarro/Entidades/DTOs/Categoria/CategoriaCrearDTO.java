package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaCrearDTO(
        @NotBlank(message = "El nombre de categoría es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre
) {
}
