package com.MposGlobal.Inventario_JuanNavarro.Servicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Producto;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import com.MposGlobal.Inventario_JuanNavarro.InterfacesServicios.TagInterfazServicio;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.TagRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServicio implements TagInterfazServicio {

    @Autowired
    private TagRepositorio tagRepositorio;

    //para enlistar todos los tags
    @Override
    public List<TagRespDTO> listarTags() {
        return tagRepositorio.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }


    @Override
    public TagRespDTO crearTag(String nombre) throws BusinessException {
        Tag tag = tagRepositorio.findByNombreIgnoreCase(nombre);
        if(tag == null){
            Tag t = new Tag();
            t.setNombre(nombre);
            t = this.tagRepositorio.save(t);
            return mapToResponseDTO(t);
        } else throw new BusinessException("El tag " + tag.getNombre() + " ya existe.", 409);
    }

    @Override
    public TagRespDTO buscarTag(int id) throws BusinessException {
        Optional<Tag> tag = tagRepositorio.findById(id);
        if(!tag.isEmpty()){
            return mapToResponseDTO(tag.get());
        } else throw new BusinessException("El tag con id " + tag.get().getIdTag() + " no existe.", 404);
    }

    @Override
    public TagRespDTO buscarTag(String nombre) throws BusinessException {
        Tag tag = tagRepositorio.findByNombreIgnoreCase(nombre);
        if(tag != null){
            return mapToResponseDTO(tag);
        } else throw new BusinessException("El tag con id " + tag.getNombre() + " no existe.", 404);
    }

    @Override
    public String modificarTag(TagActDTO tagdto) throws BusinessException {
        // Buscar el tag por ID
        Tag tag = this.tagRepositorio.findById(tagdto.idTag())
                .orElseThrow(() -> new BusinessException("El tag con id " + tagdto.idTag() + " no existe.", 404));

        // Verificar si existe otro tag con el mismo nombre
        Tag existente = this.tagRepositorio.findByNombreIgnoreCase(tagdto.nombre());

        if (existente != null && !(existente.getIdTag() == tagdto.idTag())) {
            // Existe otro tag distinto con el mismo nombre
            throw new BusinessException("El tag con nombre " + existente.getNombre() + " ya existe.", 409);
        }

        // Actualizar el nombre
        tag.setNombre(tagdto.nombre());

        // Guardar cambios
        this.tagRepositorio.save(tag);

        return "Modificación del tag exitosa";
    }

    //para mapear las listas
    private TagRespDTO mapToResponseDTO(Tag tag) {
        return new TagRespDTO(
                tag.getIdTag(),
                tag.getNombre(),
                tag.getProductos() == null ? List.of() :
                        tag.getProductos().stream().map(Producto::getNombre).toList()
        );
    }
}
