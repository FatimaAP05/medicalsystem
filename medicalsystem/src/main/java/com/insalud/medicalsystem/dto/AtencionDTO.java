package com.insalud.medicalsystem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtencionDTO {
    private Long id;
    private LocalDateTime fecha;
    private String motivo;
    private String pacienteNombre;
    private String medicoNombre;
    private String estado;
}
