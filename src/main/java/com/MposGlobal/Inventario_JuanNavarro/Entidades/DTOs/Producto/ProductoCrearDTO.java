package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductoCrearDTO(
        String nombre,
        int categoriaId,
        BigDecimal costo,
        BigDecimal precio,
        Set<Integer> tagsIds
) {
}
