
package com.insalud.medicalsystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insalud.medicalsystem.dto.AtencionDTO;
import com.insalud.medicalsystem.dto.AtencionRequestDTO;
import com.insalud.medicalsystem.model.Paciente;
import com.insalud.medicalsystem.model.Usuario;
import com.insalud.medicalsystem.repository.PacienteRepository;
import com.insalud.medicalsystem.repository.UsuarioRepository;
import com.insalud.medicalsystem.service.AtencionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final AtencionService atencionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AtencionDTO>> getAllAtenciones() {
        List<AtencionDTO> atenciones = atencionService.findAll();
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/mias")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<AtencionDTO>> getMisAtenciones(Authentication authentication) {
        String username = authentication.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(username);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Usuario usuario = usuarioOpt.get();
        // Buscar paciente por persona
        Optional<Paciente> pacienteOpt = pacienteRepository.findAll().stream()
            .filter(p -> p.getPersona() != null && p.getPersona().getId().equals(usuario.getPersona().getId()))
            .findFirst();
        if (pacienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Long pacienteId = pacienteOpt.get().getId();
        List<AtencionDTO> atenciones = atencionService.findAtencionesByPacienteId(pacienteId);
        return ResponseEntity.ok(atenciones);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<AtencionDTO> createAtencion(@Valid @RequestBody AtencionRequestDTO dto) {
        AtencionDTO created = atencionService.createAtencion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<AtencionDTO> updateAtencion(@PathVariable Long id, @Valid @RequestBody AtencionRequestDTO dto) {
        dto.setId(id);
        AtencionDTO updated = atencionService.updateAtencion(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAtencion(@PathVariable Long id) {
        atencionService.deleteAtencion(id);
        return ResponseEntity.noContent().build();
    }

    
    // Buscar atenciones por fecha
    @GetMapping("/fecha/{fecha}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<List<AtencionDTO>> getAtencionesPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<AtencionDTO> atenciones = atencionService.findAtencionesByFecha(fecha);
        return ResponseEntity.ok(atenciones);
    }

    // Buscar atenciones por id de empleado (médico)
    @GetMapping("/medico/{empleadoId}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDICO')")
    public ResponseEntity<List<AtencionDTO>> getAtencionesPorEmpleado(@PathVariable Long empleadoId) {
        List<AtencionDTO> atenciones = atencionService.findAtencionesByEmpleadoId(empleadoId);
        return ResponseEntity.ok(atenciones);
    }

}
