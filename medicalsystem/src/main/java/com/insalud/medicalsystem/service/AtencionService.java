package com.insalud.medicalsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.insalud.medicalsystem.dto.AtencionDTO;
import com.insalud.medicalsystem.dto.AtencionRequestDTO;

public interface AtencionService {
    AtencionDTO createAtencion(AtencionRequestDTO dto);
    List<AtencionDTO> findAtencionesByFecha(LocalDate fecha);
    List<AtencionDTO> findAtencionesByPacienteId(Long pacienteId);
    List<AtencionDTO> findAll();
    List<AtencionDTO> findAtencionesByEmpleadoId(Long empleadoId);
    AtencionDTO updateAtencion(AtencionRequestDTO dto);
    void deleteAtencion(Long id);
}
