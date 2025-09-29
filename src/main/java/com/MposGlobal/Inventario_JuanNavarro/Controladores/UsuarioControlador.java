package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.UsuarioServicio;
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
@RequestMapping("/usuarios")
@Validated
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    //crear usuario nuevo
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioCrearDTO ucrearDto) throws BusinessException{
        try{
            UsuarioRespDTO urespDto = this.usuarioServicio.crearUsuario(ucrearDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(urespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //enlistar usuarios activos
    @GetMapping("/")
    public ResponseEntity<?> listarUsuarios(){
        List<UsuarioRespDTO> listUsuarios = this.usuarioServicio.listarUsuarios();
        if(listUsuarios.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay usuarios");
        else return ResponseEntity.status(HttpStatus.OK).body(listUsuarios);
    }

    //obtener usuario por id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarUsuario(@Valid @PathVariable int id) throws BusinessException{
        try{
            UsuarioRespDTO urespDto = this.usuarioServicio.buscarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body(urespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //obtener usuario por nombreUsuario
    @GetMapping("/username/{username}")
    public ResponseEntity<?> buscarUsuario(@Valid @PathVariable @NotBlank String username) throws BusinessException{
        try{
            UsuarioRespDTO urespDto = this.usuarioServicio.buscarUsuario(username);
            return ResponseEntity.status(HttpStatus.OK).body(urespDto);
        }catch(BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar usuario por id
    @PutMapping("/id/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@Valid @PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioServicio.desactivarUsuario(id));
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //desactivar usuario por username
    @PutMapping("/username/{username}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@Valid @PathVariable @NotBlank String username) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioServicio.desactivarUsuario(username));
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar usuario por id
    @PutMapping("/id/{id}/activar")
    public ResponseEntity<?> activarUsuario(@Valid @PathVariable @Min(1) int id) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioServicio.activarUsuario(id));
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //activar usuario por username
    @PutMapping("/username/{username}/activar")
    public ResponseEntity<?> activarUsuario(@Valid @PathVariable @NotBlank String username) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioServicio.activarUsuario(username));
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

    //actualizar un usuario
    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody UsuarioActDTO uActDto) throws BusinessException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioServicio.actualizarUsuario(uActDto));
        }catch (BusinessException be){
            return ResponseEntity
                    .status(be.getStatus())
                    .body(be.getMessage());
        }
    }

}
