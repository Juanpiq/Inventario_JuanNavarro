package com.MposGlobal.Inventario_JuanNavarro.Seguridad;

import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    // Clave secreta, debe ser al menos 256 bits (32 bytes)
    private static final String SECRET = "MposGlobalKeyMposGlobalKey123456";

    // Tiempo de expiración: 10 minutos
    private static final long EXPIRATION = 1000 * 60 * 10;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generar token a partir de claims y username
    public String generarToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarToken(String username) {
        return generarToken(Map.of(), username);
    }

    // Extraer username del token
    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar token
    public boolean esTokenValido(String token, String username) {
        try {
            String tokenUsername = extraerUsername(token);
            return tokenUsername.equals(username) && !estaExpirado(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean estaExpirado(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
