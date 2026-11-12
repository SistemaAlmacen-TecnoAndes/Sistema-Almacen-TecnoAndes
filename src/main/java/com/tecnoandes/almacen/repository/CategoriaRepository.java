package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}