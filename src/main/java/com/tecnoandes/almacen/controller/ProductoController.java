package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.model.Categoria;
import com.tecnoandes.almacen.model.Producto;
import com.tecnoandes.almacen.repository.CategoriaRepository;
import com.tecnoandes.almacen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Mostrar la lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);
        // "productos/lista" buscará el archivo lista.html en templates/productos/
        return "productos/lista"; 
    }

    // Mostrar formulario para nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/formulario";
    }

    // Mostrar formulario para EDITAR un producto existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        // Buscamos el producto por ID. Si no existe, lanzamos un error (o redirigimos)
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + id));
        
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/formulario"; // Reutilizamos el mismo formulario
    }

        // Guardar o Actualizar el producto (desde el formulario)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        // Si el producto ya tiene un ID, significa que estamos editando.
        if (producto.getId() != null) {
            // Buscamos el producto original para no perder la fecha de creación
            Producto productoExistente = productoService.buscarPorId(producto.getId()).orElse(null);
            if (productoExistente != null) {
                producto.setFechaCreacion(productoExistente.getFechaCreacion());
            }
        }
        // Guardar (si tiene ID, actualiza; si no, crea uno nuevo)
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    // Eliminar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }
}