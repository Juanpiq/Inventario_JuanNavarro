package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record ProductoRespDTO(
        int idProducto,
        String nombre,
        String categoriaNombre,
        BigDecimal costo,
        BigDecimal precio,
        boolean activo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        Set<String> tags
) {
}
