package com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios;

import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;

import java.util.List;

public interface TagInterfazServicio {

    List<Tag> listaTags();

    Tag crearTag(Tag tag);

    Tag buscarTag(int id);

    Tag buscarTag(String nombre);

    Tag modificarTag(Tag tag);
}
