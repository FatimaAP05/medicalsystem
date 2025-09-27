package com.insalud.medicalsystem.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtencionRequestDTO {
    private Long id;
    @NotNull
    private LocalDateTime fecha;
    @NotBlank
    private String motivo;
    @NotNull
    private Long pacienteId;
    @NotNull
    private Long empleadoId;
    @NotBlank
    private String estado;
}
