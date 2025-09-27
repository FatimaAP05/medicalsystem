package com.insalud.medicalsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insalud.medicalsystem.model.Especialidad;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    List<Especialidad> findByEstado(String estado);
}
