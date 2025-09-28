package com.MposGlobal.Inventario_JuanNavarro.Seguridad;

import com.MposGlobal.Inventario_JuanNavarro.Servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UsuarioServicio usuarioServicio;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF, ya que usamos JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Login y creación inicial de usuario
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").hasRole("ADMIN") // Solo admin puede crear
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN")  // Solo admin puede actualizar
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/**/activar").hasRole("ADMIN") // Activar/desactivar
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/**/desactivar").hasRole("ADMIN")
                        .anyRequest().authenticated() // Todos los demás endpoints requieren token
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless JWT
                .userDetailsService(usuarioServicio)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Hasheo de passwords
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
