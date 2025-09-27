package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag;

import java.util.List;

public record TagRespDTO(
        int idTag,
        String nombre,
        List<String> productos
) {
}
