package com.tecnoandes.almacen.service;

import com.tecnoandes.almacen.model.Producto;
import com.tecnoandes.almacen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Guardar o actualizar un producto
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    // Buscar un producto por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Eliminar un producto
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
    
    // Obtener productos con stock bajo (para el dashboard)
    public List<Producto> obtenerStockCritico() {
        // Lógica simple: productos con stock menor a 5
        return productoRepository.findByStockLessThanEqual(5);
    }
}
