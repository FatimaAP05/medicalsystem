package com.insalud.medicalsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insalud.medicalsystem.model.Atencion;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    @Query("SELECT a FROM Atencion a WHERE DATE(a.fecha) = :fecha")
    List<Atencion> findAllByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT a FROM Atencion a WHERE a.empleado.id = :empleadoId")
    List<Atencion> findAllByEmpleadoId(@Param("empleadoId") Long empleadoId);
}
