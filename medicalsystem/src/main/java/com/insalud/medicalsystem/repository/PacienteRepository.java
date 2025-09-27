package com.insalud.medicalsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insalud.medicalsystem.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByEstado(String estado);
}
