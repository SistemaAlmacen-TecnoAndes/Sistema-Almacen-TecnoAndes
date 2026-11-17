package com.tecnoandes.almacen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Redirigir la raíz (localhost:8080) directamente al LOGIN
    @GetMapping("/")
    public String redirigirAlLogin() {
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login"; 
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/index"; 
    }
}
