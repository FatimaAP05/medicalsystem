package com.insalud.medicalsystem.service;

import java.util.List;
import java.util.Optional;

import com.insalud.medicalsystem.dto.PersonaDTO;

public interface PersonaService {
    PersonaDTO createPersona(PersonaDTO dto);
    Optional<PersonaDTO> getPersonaById(Long id);
    List<PersonaDTO> getAllPersonas();
}
