package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.model.ConfiguracionSistema;
import com.tecnoandes.almacen.service.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @GetMapping
    public String mostrarConfiguracion(Model model) {
        model.addAttribute("config", configuracionService.obtenerConfiguracion());
        return "configuracion/config_index";
    }

    @PostMapping("/guardar")
    public String guardarConfiguracion(@ModelAttribute("config") ConfiguracionSistema config, 
                                       Authentication auth, 
                                       RedirectAttributes flash) {
        configuracionService.actualizarConfiguracion(config, auth.getName());
        flash.addFlashAttribute("success", "Configuración actualizada correctamente.");
        return "redirect:/configuracion";
    }
}