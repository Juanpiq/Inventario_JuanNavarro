package com.MposGlobal.Inventario_JuanNavarro.Bitacora;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AccessLogFilter extends OncePerRequestFilter {

    private static final String LOG_FILE = "src/main/java/com/MposGlobal/Inventario_JuanNavarro/Bitacora/access.log";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //obtencion del usuario
        Object principal = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;

        String username = "ANONIMO";
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        }

        //Hora del acceso
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        //Endpoint
        String method = request.getMethod();
        String uri = request.getRequestURI();

        //Escribir en el archivo
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(String.format("[%s] Usuario: %s | Método: %s | Endpoint: %s%n", timestamp, username, method, uri));
        } catch (IOException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
