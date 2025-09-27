package com.insalud.medicalsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    private String usuario;

    @NotBlank
    @Size(min = 6)
    private String contrasena;

    @NotNull
    private Long personaId;
}
