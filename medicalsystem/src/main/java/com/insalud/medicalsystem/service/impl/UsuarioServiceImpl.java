package com.insalud.medicalsystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insalud.medicalsystem.dto.UsuarioDTO;
import com.insalud.medicalsystem.model.Persona;
import com.insalud.medicalsystem.model.Usuario;
import com.insalud.medicalsystem.repository.PersonaRepository;
import com.insalud.medicalsystem.repository.UsuarioRepository;
import com.insalud.medicalsystem.service.UsuarioService;
import com.insalud.medicalsystem.service.exception.DuplicateResourceException;
import com.insalud.medicalsystem.service.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    @Override
    @Transactional
    public UsuarioDTO registerUsuario(UsuarioDTO dto) {
        if (usuarioRepository.findByUsuario(dto.getUsuario()).isPresent()) {
            throw new DuplicateResourceException("El usuario ya existe");
        }
        Persona persona = personaRepository.findById(dto.getPersonaId())
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));
        Usuario usuario = new Usuario();
        usuario.setUsuario(dto.getUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setPersona(persona);
        usuario = usuarioRepository.save(usuario);
        dto.setId(usuario.getId());
        return dto;
    }

    @Override
    public Optional<UsuarioDTO> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario)
                .map(u -> new UsuarioDTO(u.getId(), u.getUsuario(), u.getContrasena(), u.getPersona().getId()));
    }
}
