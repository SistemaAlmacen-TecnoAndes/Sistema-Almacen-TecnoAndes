package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.ConfiguracionSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends JpaRepository<ConfiguracionSistema, Long> {
    
}