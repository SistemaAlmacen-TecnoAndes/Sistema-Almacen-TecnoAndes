package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
