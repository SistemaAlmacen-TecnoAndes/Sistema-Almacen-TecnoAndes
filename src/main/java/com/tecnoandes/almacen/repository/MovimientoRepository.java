package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    // Cambiamos un poco el nombre para que Spring no se confunda
    List<Movimiento> findAllByOrderByFechaDesc();
}