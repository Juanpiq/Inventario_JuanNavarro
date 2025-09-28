package com.MposGlobal.Inventario_JuanNavarro.Servicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Usuario;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.UsuarioRepositorio;
import com.MposGlobal.Inventario_JuanNavarro.Seguridad.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String nombreUsuario, String clave) throws BusinessException {
        Usuario usuario = usuarioRepositorio.findByNombreUsuarioIgnoreCase(nombreUsuario);

        if (usuario == null || !usuario.isEnabled()) {
            throw new BusinessException("Usuario no encontrado o desactivado", 404);
        }

        // Verifica la clave
        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new BusinessException("Contraseña incorrecta", 401);
        }

        // Actualiza fecha de último ingreso
        usuario.setFechaUltimoIngreso(LocalDateTime.now());
        usuarioRepositorio.save(usuario);

        // Genera el JWT
        return jwtService.generarToken(usuario.getNombreUsuario());
    }
}
