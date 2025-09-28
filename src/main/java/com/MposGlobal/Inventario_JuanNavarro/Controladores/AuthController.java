package com.MposGlobal.Inventario_JuanNavarro.Controladores;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioLoginDTO;
import com.MposGlobal.Inventario_JuanNavarro.Servicios.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        try {
            String token = authService.login(loginDTO.nombreUsuario(), loginDTO.clave());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (BusinessException e) {
            return ResponseEntity.status(e.getStatus()).body(Map.of("error", e.getMessage()));
        }
    }
}
