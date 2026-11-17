package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.service.InventarioService;
import com.tecnoandes.almacen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    // 1. ENTRADAS (Carpeta suelta en templates)
    @GetMapping("/entradas")
    public String mostrarEntradas(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("movimientos", inventarioService.listarMovimientos()
                .stream().filter(m -> m.getTipo().equals("ENTRADA")).toList());
        return "entradas/entradas"; 
    }

    // 2. SALIDAS (Carpeta suelta en templates)
    @GetMapping("/salidas")
    public String mostrarSalidas(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("movimientos", inventarioService.listarMovimientos()
                .stream().filter(m -> m.getTipo().equals("SALIDA")).toList());
        return "salidas/salidas"; 
    }

    // 3. KARDEX (Dentro de la carpeta inventario)
    @GetMapping("/kardex")
    public String mostrarKardex(Model model) {
        model.addAttribute("movimientos", inventarioService.listarMovimientos());
        return "inventario/kardex_inventario"; 
    }

    // 4. REGISTRAR
    @PostMapping("/registrar")
    public String registrarMovimiento(@RequestParam Long productoId,
                                      @RequestParam String tipo,
                                      @RequestParam Integer cantidad,
                                      Authentication auth,
                                      RedirectAttributes redirectAttributes) {
        try {
            inventarioService.registrarMovimiento(productoId, tipo, cantidad, auth.getName());
            redirectAttributes.addFlashAttribute("success", "Movimiento registrado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        if (tipo.equals("ENTRADA")) {
            return "redirect:/inventario/entradas";
        } else {
            return "redirect:/inventario/salidas";
        }
    }
}