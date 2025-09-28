package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record ProductoActDTO(
        @NotNull(message = "El id del producto es obligatorio")
        @Positive(message = "El id de categoría debe ser mayor que 0")
        Integer id,

        @NotBlank(message = "El nombre de categoría es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotNull(message = "El id de la categoria a la cual pertenecerá es obligatorio")
        @Positive(message = "El ID de categoría debe ser mayor que 0")
        Integer categoriaId,

        @NotNull(message = "El costo es obligatorio")
        @Positive(message = "El costo debe ser mayor que 0")
        BigDecimal costo,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que 0")
        BigDecimal precio,

        Set<Integer> tagsIds
) {
}
