package com.insalud.medicalsystem.service;

import java.util.Optional;

import com.insalud.medicalsystem.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO registerUsuario(UsuarioDTO dto);
    Optional<UsuarioDTO> findByUsuario(String usuario);
}
