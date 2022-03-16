package com.example.registrationsystem.security;

import com.example.registrationsystem.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private static final Long EXPIRATION_TIME = 5 * 60 * 60 * 1000L;
    private static final String KEY = "ThisKeySecretKeyForJWT";

    public String generateToken(String username, Set<Role> roles){

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .claim("roles", roles)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts
                .parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

