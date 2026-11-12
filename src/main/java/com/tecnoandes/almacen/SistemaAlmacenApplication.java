package com.tecnoandes.almacen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaAlmacenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAlmacenApplication.class, args);
        
        System.out.println("=================================================");
        System.out.println("  LOGISTOCK WMS - TECNOANDES PERU S.A.C.");
        System.out.println("  Servidor iniciado correctamente");
        System.out.println("  URL: http://localhost:8080");
        System.out.println("=================================================");
    }
}