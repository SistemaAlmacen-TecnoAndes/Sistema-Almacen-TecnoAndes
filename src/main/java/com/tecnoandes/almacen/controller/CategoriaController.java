package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.model.Categoria;
import com.tecnoandes.almacen.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

        // Listar todas las categorías
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/lista"; 
    }

    // Mostrar formulario para nueva categoría o editar
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario"; 
    }

    // Mostrar formulario para editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de categoría inválido: " + id));
        model.addAttribute("categoria", categoria);
        return "categorias/formulario"; 
    }
}
