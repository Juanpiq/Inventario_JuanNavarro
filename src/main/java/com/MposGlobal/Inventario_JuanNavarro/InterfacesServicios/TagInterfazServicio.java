package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;

import java.util.List;

public interface TagInterfazServicio {

    List<TagRespDTO> listarTags();

    TagRespDTO crearTag(String nombre) throws BusinessException;

    TagRespDTO buscarTag(int id) throws BusinessException;

    TagRespDTO buscarTag(String nombre) throws BusinessException;

    String modificarTag(TagActDTO tag) throws BusinessException;
}
