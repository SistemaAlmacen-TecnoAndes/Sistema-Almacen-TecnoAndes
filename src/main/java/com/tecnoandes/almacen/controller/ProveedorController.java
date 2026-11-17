package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.model.Proveedor;
import com.tecnoandes.almacen.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

        @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "proveedores/p_lista"; 
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/p_formulario"; 
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("proveedor", proveedor);
        return "proveedores/p_formulario"; 
    }
}