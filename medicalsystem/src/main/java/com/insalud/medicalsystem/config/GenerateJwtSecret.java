package com.insalud.medicalsystem.config;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;

public class GenerateJwtSecret {
    public static void main(String[] args) {
        // Genera una clave segura de 256 bits para HS256
        String secret = Base64.getEncoder().encodeToString(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
        );
        System.out.println("JWT Secret (Base64): " + secret);
    }
}
