package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.model.ConfiguracionSistema;
import com.tecnoandes.almacen.model.Producto;
import com.tecnoandes.almacen.service.ConfiguracionService;
import com.tecnoandes.almacen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private ConfiguracionService configuracionService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String redirigirAlLogin() {
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login"; 
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1. Obtener la configuración actual (para saber el stock mínimo)
        ConfiguracionSistema config = configuracionService.obtenerConfiguracion();

        // 2. Buscar los productos que están por debajo del stock mínimo de la configuración
        List<Producto> productosCriticos = productoService.buscarStockCritico(config.getStockMinimoDefecto());

        // 3. Enviar los datos al HTML
        model.addAttribute("cantidadCriticos", productosCriticos.size());
        model.addAttribute("productosCriticos", productosCriticos);
        model.addAttribute("config", config);

        return "dashboard/index"; 
    }
}
