package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
