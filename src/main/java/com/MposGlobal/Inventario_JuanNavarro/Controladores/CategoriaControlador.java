package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Categoria.CategoriaRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.CategoriaServicio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Validated
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio categoriaServicio;

    //Crear categoria
    @PostMapping("/crear")
    public ResponseEntity<?> crearCategoria(@Valid @RequestBody CategoriaCrearDTO catdto) throws BusinessException {
        try{
            CategoriaRespDTO catrespdto = this.categoriaServicio.crearCategoria(catdto);
            return ResponseEntity.status(HttpStatus.CREATED).body(catrespdto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //Lista de Categorias (solo las activas)
    @GetMapping("/")
    public ResponseEntity<?> listarCategorias(){
        List<CategoriaRespDTO> lCategorias = this.categoriaServicio.listarCategorias();
        if(lCategorias.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay categorias vigentes actualmente");
        else return ResponseEntity.status(HttpStatus.OK).body(lCategorias);
    }

    //obtener categoria por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarCategoria(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            CategoriaRespDTO catdto= this.categoriaServicio.buscarCategoria(id);
            return ResponseEntity.status(HttpStatus.OK).body(catdto);
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //obtener productos de la categoria por su id
    @GetMapping("/id/{id}/productos")
    public ResponseEntity<?> listarProductosCategoria(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            List<ProductoRespDTO> listProductos = this.categoriaServicio.listarProductosCategoria(id);
            if(listProductos.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Esta categoria no tiene productos asignados aun");
            else return ResponseEntity.status(HttpStatus.OK).body(listProductos);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //obtener categoria por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarCategoria(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            CategoriaRespDTO catdto= this.categoriaServicio.buscarCategoria(nombre);
            return ResponseEntity.status(HttpStatus.OK).body(catdto);
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //modificar una categoria
    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarCategoria(@Valid @RequestBody CategoriaActDTO catdto) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaServicio.modificarCategoria(catdto));
        }catch(BusinessException be) {
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar categoria por id (desactiva todos los productos asociados a esta)
    @PutMapping("/id/{id}/desactivar")
    public ResponseEntity<?> desactivarCategoria(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaServicio.desactivarCategoria(id));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar categoria por nombre (desactiva todos los productos asociados a esta)
    @PutMapping("/nombre/{nombre}/desactivar")
    public ResponseEntity<?> desactivarCategoria(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaServicio.desactivarCategoria(nombre));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar categoria por id (no activará los productos asociados)
    @PutMapping("/id/{id}/activar")
    public ResponseEntity<?> activarCategoria(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaServicio.activarCategoria(id));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar categoria por nombre (desactiva todos los productos asociados a esta)
    @PutMapping("/nombre/{nombre}/activar")
    public ResponseEntity<?> activarCategoria(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.categoriaServicio.activarCategoria(nombre));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }
}
