package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Producto.ProductoRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.ProductoServicio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    //Para crear un producto nuevo
    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@Valid @RequestBody ProductoCrearDTO prodCrearDTO) throws BusinessException {
        try{
            ProductoRespDTO prodrespDto = this.productoServicio.crearProducto(prodCrearDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(prodrespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //enlistar todos los productos (activos)
    @GetMapping("/")
    public ResponseEntity<?> listarProductos(){
        List<ProductoRespDTO> productoRespDTOS = this.productoServicio.listarProductos();

        if(productoRespDTOS.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Esta categoria no tiene productos asignados");
        else return ResponseEntity.status(HttpStatus.OK).body(productoRespDTOS);
    }

    //obtener producto por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarProducto(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            ProductoRespDTO prodrespDto = this.productoServicio.buscarProducto(id);
            return ResponseEntity.status(HttpStatus.OK).body(prodrespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //obtener producto por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarProducto(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            ProductoRespDTO prodrespDto = this.productoServicio.buscarProducto(nombre);
            return ResponseEntity.status(HttpStatus.OK).body(prodrespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar producto por id
    @PutMapping("/id/{id}/desactivar")
    public ResponseEntity<?> desactivarProducto(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productoServicio.desactivarProducto(id));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar producto por nombre
    @PutMapping("/nombre/{nombre}/desactivar")
    public ResponseEntity<?> desactivarProducto(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productoServicio.desactivarProducto(nombre));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar producto por id
    @PutMapping("/id/{id}/activar")
    public ResponseEntity<?> activarProducto(@PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productoServicio.desactivarProducto(id));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar producto por nombre
    @PutMapping("/nombre/{nombre}/activar")
    public ResponseEntity<?> activarProducto(@PathVariable @NotBlank String nombre) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productoServicio.desactivarProducto(nombre));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //actualizar producto
    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarProducto(@Valid @RequestBody ProductoActDTO prodActDto) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.productoServicio.modificarProducto(prodActDto));
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }
}
