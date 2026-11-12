package com.tecnoandes.almacen.service;

import com.tecnoandes.almacen.model.Movimiento;
import com.tecnoandes.almacen.model.Producto;
import com.tecnoandes.almacen.model.Usuario;
import com.tecnoandes.almacen.repository.MovimientoRepository;
import com.tecnoandes.almacen.repository.ProductoRepository;
import com.tecnoandes.almacen.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void registrarMovimiento(Long productoId, String tipo, Integer cantidad, String username) {
        // 1. Buscar el producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // 2. Buscar el usuario que hace el movimiento
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Guardar el stock anterior
        Integer stockAnterior = producto.getStock();
        Integer stockNuevo = stockAnterior;

        // 4. Actualizar el stock según el tipo
        if (tipo.equals("ENTRADA")) {
            stockNuevo += cantidad;
        } else if (tipo.equals("SALIDA")) {
            if (stockAnterior < cantidad) {
                throw new RuntimeException("Stock insuficiente para realizar la salida.");
            }
            stockNuevo -= cantidad;
        }

        // 5. Guardar el nuevo stock en el producto
        producto.setStock(stockNuevo);
        productoRepository.save(producto);

        // 6. Crear el registro en el Kardex (Movimiento)
        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockNuevo(stockNuevo);
        movimiento.setUsuario(usuario);
        movimiento.setFecha(LocalDateTime.now());
        
        movimientoRepository.save(movimiento);
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAllByOrderByFechaDesc();
    }
}