

package com.insalud.medicalsystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insalud.medicalsystem.dto.AtencionDTO;
import com.insalud.medicalsystem.dto.AtencionRequestDTO;
import com.insalud.medicalsystem.model.Atencion;
import com.insalud.medicalsystem.model.Empleado;
import com.insalud.medicalsystem.model.Paciente;
import com.insalud.medicalsystem.repository.AtencionRepository;
import com.insalud.medicalsystem.repository.EmpleadoRepository;
import com.insalud.medicalsystem.repository.PacienteRepository;
import com.insalud.medicalsystem.service.AtencionService;
import com.insalud.medicalsystem.service.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtencionServiceImpl implements AtencionService {
    private final AtencionRepository atencionRepository;
    private final PacienteRepository pacienteRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public AtencionDTO createAtencion(AtencionRequestDTO dto) {
    Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
        .filter(p -> "ACTIVO".equalsIgnoreCase(p.getEstado()))
        .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado o inactivo"));
    Empleado empleado = empleadoRepository.findById(dto.getEmpleadoId())
        .filter(e -> "ACTIVO".equalsIgnoreCase(e.getEstado()))
        .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado o inactivo"));
    Atencion atencion = new Atencion();
    atencion.setFecha(dto.getFecha());
    atencion.setMotivo(dto.getMotivo());
    atencion.setPaciente(paciente);
    atencion.setEmpleado(empleado);
    atencion.setEstado(dto.getEstado());
    atencion = atencionRepository.save(atencion);
    return new AtencionDTO(
        atencion.getId(),
        atencion.getFecha(),
        atencion.getMotivo(),
        paciente.getPersona().getNombre(),
        empleado.getPersona().getNombre(),
        atencion.getEstado()
    );
    }

        @Override
    public List<AtencionDTO> findAtencionesByEmpleadoId(Long empleadoId) {
        return atencionRepository.findAllByEmpleadoId(empleadoId).stream()
            .map(a -> new AtencionDTO(
                a.getId(),
                a.getFecha(),
                a.getMotivo(),
                a.getPaciente() != null && a.getPaciente().getPersona() != null ? a.getPaciente().getPersona().getNombre() : null,
                a.getEmpleado() != null && a.getEmpleado().getPersona() != null ? a.getEmpleado().getPersona().getNombre() : null,
                a.getEstado()
            ))
            .collect(Collectors.toList());
    }

        @Override
    public List<AtencionDTO> findAll() {
        return atencionRepository.findAll().stream()
            .map(a -> new AtencionDTO(
                a.getId(),
                a.getFecha(),
                a.getMotivo(),
                a.getPaciente() != null && a.getPaciente().getPersona() != null ? a.getPaciente().getPersona().getNombre() : null,
                a.getEmpleado() != null && a.getEmpleado().getPersona() != null ? a.getEmpleado().getPersona().getNombre() : null,
                a.getEstado()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<AtencionDTO> findAtencionesByFecha(LocalDate fecha) {
        LocalDateTime start = fecha.atStartOfDay();
        LocalDateTime end = fecha.plusDays(1).atStartOfDay();
    return atencionRepository.findAll().stream()
        .filter(a -> a.getFecha() != null && !a.getFecha().isBefore(start) && a.getFecha().isBefore(end))
        .map(a -> new AtencionDTO(
            a.getId(),
            a.getFecha(),
            a.getMotivo(),
            a.getPaciente() != null && a.getPaciente().getPersona() != null ? a.getPaciente().getPersona().getNombre() : null,
            a.getEmpleado() != null && a.getEmpleado().getPersona() != null ? a.getEmpleado().getPersona().getNombre() : null,
            a.getEstado()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<AtencionDTO> findAtencionesByPacienteId(Long pacienteId) {
    return atencionRepository.findAll().stream()
        .filter(a -> a.getPaciente() != null && a.getPaciente().getId().equals(pacienteId))
        .map(a -> new AtencionDTO(
            a.getId(),
            a.getFecha(),
            a.getMotivo(),
            a.getPaciente() != null && a.getPaciente().getPersona() != null ? a.getPaciente().getPersona().getNombre() : null,
            a.getEmpleado() != null && a.getEmpleado().getPersona() != null ? a.getEmpleado().getPersona().getNombre() : null,
            a.getEstado()
        ))
        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AtencionDTO updateAtencion(AtencionRequestDTO dto) {
    Atencion atencion = atencionRepository.findById(dto.getId())
        .orElseThrow(() -> new EntityNotFoundException("Atención no encontrada"));
    if (dto.getFecha() != null) atencion.setFecha(dto.getFecha());
    if (dto.getMotivo() != null) atencion.setMotivo(dto.getMotivo());
    if (dto.getEstado() != null) atencion.setEstado(dto.getEstado());
    // Opcional: actualizar paciente/empleado si se requiere
    atencion = atencionRepository.save(atencion);
    Paciente paciente = atencion.getPaciente();
    Empleado empleado = atencion.getEmpleado();
    return new AtencionDTO(
        atencion.getId(),
        atencion.getFecha(),
        atencion.getMotivo(),
        paciente != null && paciente.getPersona() != null ? paciente.getPersona().getNombre() : null,
        empleado != null && empleado.getPersona() != null ? empleado.getPersona().getNombre() : null,
        atencion.getEstado()
    );
    }

    @Override
    @Transactional
    public void deleteAtencion(Long id) {
        if (!atencionRepository.existsById(id)) {
            throw new EntityNotFoundException("Atención no encontrada");
        }
        atencionRepository.deleteById(id);
    }
}
