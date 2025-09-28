package com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria;

import java.time.LocalDateTime;
import java.util.List;

public record CategoriaRespDTO(
        int idCategoria,
        String nombre,
        boolean activo,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        List<String> productos

) {
}
