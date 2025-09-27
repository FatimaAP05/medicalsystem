package com.insalud.medicalsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insalud.medicalsystem.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByRol(String rol);
}
