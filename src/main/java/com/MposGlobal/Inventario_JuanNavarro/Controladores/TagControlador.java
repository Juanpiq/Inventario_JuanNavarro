package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.TagServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagControlador {

    @Autowired
    private TagServicio tagServicio;

    //para enlistar todos los tags existentes
    @GetMapping("/")
    public ResponseEntity<?> listarTags(){
        List<TagRespDTO> lista = this.tagServicio.listarTags();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //buscar un tag por su id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTag(@PathVariable int id) throws BusinessException {
        try{
            TagRespDTO tagdto = this.tagServicio.buscarTag(id);
            return ResponseEntity.status(HttpStatus.OK).body(tagdto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //buscar un tag por su id
    @GetMapping("/{nombre}")
    public ResponseEntity<?> buscarTag(@PathVariable String nombre) throws BusinessException {
        try{
            TagRespDTO tagdto = this.tagServicio.buscarTag(nombre);
            return ResponseEntity.status(HttpStatus.OK).body(tagdto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //crear un nuevo tag
    @PostMapping("/crear")
    public ResponseEntity<?> crearTag(Tag tag) throws BusinessException{
        try{
            tag = this.tagServicio.crearTag(tag.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(tag);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }
}
