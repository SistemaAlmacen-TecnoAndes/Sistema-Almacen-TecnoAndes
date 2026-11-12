package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByCodigo(String codigo);
    
    // Buscar productos que tengan stock menor o igual al stock mínimo (Para las alertas)
    List<Producto> findByStockLessThanEqual(Integer stock);
}