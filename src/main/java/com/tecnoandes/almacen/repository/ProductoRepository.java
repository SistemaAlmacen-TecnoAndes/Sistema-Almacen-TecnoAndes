package com.tecnoandes.almacen.repository;

import com.tecnoandes.almacen.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByCodigo(String codigo);
    
    // Esta línea es la que usa el "Toque Mágico" para buscar stock bajo
    List<Producto> findByStockLessThanEqual(Integer stock);
}