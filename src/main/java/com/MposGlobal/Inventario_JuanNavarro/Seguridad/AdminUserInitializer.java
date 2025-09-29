package com.MposGlobal.Inventario_JuanNavarro.Seguridad;


import com.MposGlobal.Inventario_JuanNavarro.Entidades.Tablas.Usuario;
import com.MposGlobal.Inventario_JuanNavarro.Repositorios.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner crearAdminInicial(UsuarioRepositorio usuarioRepositorio,
                                               PasswordEncoder passwordEncoder) {
        return args -> {
            // Revisar si ya existe algún usuario con rol ADMIN
            boolean existeAdmin = usuarioRepositorio.findAll()
                    .stream()
                    .anyMatch(u -> u.getNivelAcceso().equalsIgnoreCase("ADMIN"));

            if (!existeAdmin) {
                Usuario admin = new Usuario();
                admin.setNombreCompleto("Administrador Inicial");
                admin.setNombreUsuario("admin"); // puedes cambiarlo si quieres
                admin.setNivelAcceso("ADMIN");
                admin.setClave(passwordEncoder.encode("admin123")); // contraseña inicial
                admin.setActivo(true);
                admin.setFechaCreacion(LocalDateTime.now());

                usuarioRepositorio.save(admin);
                System.out.println("Usuario ADMIN inicial creado: " + admin.getNombreUsuario());
            } else {
                System.out.println("Ya existe al menos un usuario ADMIN. No se creó uno nuevo.");
            }
        };
    }

}
