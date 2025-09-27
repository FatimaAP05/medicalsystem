package com.insalud.medicalsystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insalud.medicalsystem.dto.PersonaDTO;
import com.insalud.medicalsystem.model.Persona;
import com.insalud.medicalsystem.repository.PersonaRepository;
import com.insalud.medicalsystem.service.PersonaService;
import com.insalud.medicalsystem.service.exception.DuplicateResourceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository personaRepository;

    @Override
    @Transactional
    public PersonaDTO createPersona(PersonaDTO dto) {
        if (personaRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("El email ya está registrado");
        }
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setEmail(dto.getEmail());
        persona.setEstado(dto.getEstado());
        persona = personaRepository.save(persona);
        dto.setId(persona.getId());
        return dto;
    }

    @Override
    public Optional<PersonaDTO> getPersonaById(Long id) {
        return personaRepository.findById(id)
                .map(p -> new PersonaDTO(p.getId(), p.getNombre(), p.getEmail(), p.getEstado()));
    }

    @Override
    public List<PersonaDTO> getAllPersonas() {
        return personaRepository.findAll().stream()
                .map(p -> new PersonaDTO(p.getId(), p.getNombre(), p.getEmail(), p.getEstado()))
                .collect(Collectors.toList());
    }
}
