package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Tag.TagRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Tag;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.TagServicio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarTag(@PathVariable @Min(1) int id) throws BusinessException {
        try{
            TagRespDTO tagdto = this.tagServicio.buscarTag(id);
            return ResponseEntity.status(HttpStatus.OK).body(tagdto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //buscar un tag por su nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarTag(@PathVariable @NotBlank String nombre) throws BusinessException {
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
    public ResponseEntity<?> crearTag(@RequestBody @Valid TagCrearDTO tag) throws BusinessException{
        try{
            TagRespDTO tagdto = this.tagServicio.crearTag(tag.nombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(tag);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //modificar o actualizar tags ya existentes
    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarTag(@RequestBody @Valid TagActDTO tagdto) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.tagServicio.modificarTag(tagdto));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }
}
