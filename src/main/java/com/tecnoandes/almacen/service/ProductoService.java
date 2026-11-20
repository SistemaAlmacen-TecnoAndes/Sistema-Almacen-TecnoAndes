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

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
    
    // ESTE ES EL MÉTODO PARA EL TOQUE MÁGICO
    public List<Producto> buscarStockCritico(Integer limite) {
        return productoRepository.findByStockLessThanEqual(limite);
    }
}