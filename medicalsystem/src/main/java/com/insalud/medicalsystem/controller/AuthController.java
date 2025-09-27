package com.insalud.medicalsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insalud.medicalsystem.dto.AuthResponse;
import com.insalud.medicalsystem.dto.LoginRequest;
import com.insalud.medicalsystem.model.Usuario;
import com.insalud.medicalsystem.repository.UsuarioRepository;
import com.insalud.medicalsystem.security.jwt.JwtTokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
            System.out.println("Entró al endpoint /api/auth/login: usuario=" + loginRequest.getUsuario());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsuario(),
                            loginRequest.getPassword()
                    )
            );
            Usuario usuario = usuarioRepository.findByUsuario(loginRequest.getUsuario())
                    .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));
            if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getContrasena())) {
                throw new BadCredentialsException("Usuario o contraseña incorrectos");
            }
            String token = jwtTokenProvider.generateToken(usuario.getUsuario(), usuario.getRol());
            return ResponseEntity.ok(new AuthResponse(token, usuario.getRol()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }
    }
}
