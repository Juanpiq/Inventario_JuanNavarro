package com.MposGlobal.Inventario_JuanNavarro.Servicios;

import com.MposGlobal.Inventario_JuanNavarro.BusinessException;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioActDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioCrearDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.DTOs.Usuario.UsuarioRespDTO;
import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Usuario;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    /*** UserDetailsService - método obligatorio para Spring Security ***/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByNombreUsuarioIgnoreCase(username);
        if (usuario == null || !usuario.isEnabled()) {
            throw new UsernameNotFoundException("Usuario no encontrado o desactivado");
        }
        return usuario;
    }

    /*** Crear usuario - solo admin ***/
    public UsuarioRespDTO crearUsuario(UsuarioCrearDTO ucrearDto) throws BusinessException {
        Usuario uExistente = this.usuarioRepositorio.findByNombreUsuarioIgnoreCase(ucrearDto.nombreUsuario());
        if (uExistente == null) {
            throw new BusinessException("El nombre de usuario ya existe", 409);
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(ucrearDto.nombreCompleto());
        usuario.setNombreUsuario(ucrearDto.nombreUsuario());
        usuario.setNivelAcceso(ucrearDto.nivelAcceso().toUpperCase()); // siempre en mayúscula
        usuario.setClave(passwordEncoder.encode(ucrearDto.clave()));   // hashear password
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        Usuario guardado = usuarioRepositorio.save(usuario);
        return mapToRespDTO(guardado);
    }

    /*** Actualizar usuario - solo admin ***/
    public UsuarioRespDTO actualizarUsuario(UsuarioActDTO uActdto) throws BusinessException {
        Usuario usuario = usuarioRepositorio.findById(uActdto.id())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", 404));

        // Validar que el username nuevo no exista en otro usuario
        if (!usuario.getNombreUsuario().equalsIgnoreCase(uActdto.nombreUsuario()) &&
                usuarioRepositorio.existsByNombreUsuarioIgnoreCase(uActdto.nombreUsuario())) {
            throw new BusinessException("El nombre de usuario ya existe", 409);
        }

        usuario.setNombreCompleto(uActdto.nombreCompleto());
        usuario.setNombreUsuario(uActdto.nombreUsuario());
        usuario.setNivelAcceso(uActdto.nivelAcceso().toUpperCase());
        if (uActdto.clave() != null && !uActdto.clave().isEmpty()) {
            usuario.setClave(passwordEncoder.encode(uActdto.clave()));
        }

        Usuario actualizado = usuarioRepositorio.save(usuario);
        return mapToRespDTO(actualizado);
    }

    /*** Soft delete - desactivar usuario ***/
    public String desactivarUsuario(int id) throws BusinessException {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", 404));

        if (!usuario.isActivo()) {
            throw new BusinessException("Usuario ya estaba desactivado", 406);
        }

        usuario.setActivo(false);
        usuarioRepositorio.save(usuario);
        return "Usuario desactivado correctamente";
    }

    /*** Activar usuario ***/
    public String activarUsuario(int id) throws BusinessException {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", 404));

        if (usuario.isActivo()) {
            throw new BusinessException("Usuario ya estaba activo", 406);
        }

        usuario.setActivo(true);
        usuarioRepositorio.save(usuario);
        return "Usuario activado correctamente";
    }

    /*** Listar usuarios activos ***/
    public List<UsuarioRespDTO> listarUsuarios() {
        return usuarioRepositorio.findAll()
                .stream()
                .filter(Usuario::isActivo)
                .map(this::mapToRespDTO)
                .collect(Collectors.toList());
    }

    /*** Buscar usuario activo por ID ***/
    public UsuarioRespDTO buscarUsuario(int id) throws BusinessException {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", 404));

        if (!usuario.isActivo()) {
            throw new BusinessException("Usuario desactivado", 406);
        }

        return mapToRespDTO(usuario);
    }

    /*** Mapper privado para response DTO ***/
    private UsuarioRespDTO mapToRespDTO(Usuario u) {
        return new UsuarioRespDTO(
                u.getIdUsuario(),
                u.getNombreCompleto(),
                u.getNombreUsuario(),
                u.getNivelAcceso(),
                u.getFechaCreacion(),
                u.getFechaUltimoIngreso()
        );
    }
}
