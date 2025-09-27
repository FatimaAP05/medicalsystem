package com.insalud.medicalsystem.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123"; // aquí pones tu contraseña
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Contraseña en texto plano: " + rawPassword);
        System.out.println("Contraseña encriptada (BCrypt): " + encodedPassword);
    }
}
