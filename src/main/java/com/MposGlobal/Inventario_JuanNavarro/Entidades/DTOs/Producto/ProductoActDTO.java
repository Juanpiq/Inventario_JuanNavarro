package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductoActDTO(
        int id,
        String nombre,
        BigDecimal costo,
        BigDecimal precio,
        Set<Integer> tagsIds
) {
}
