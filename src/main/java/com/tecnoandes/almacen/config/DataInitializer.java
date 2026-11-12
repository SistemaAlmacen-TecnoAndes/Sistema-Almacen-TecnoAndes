package com.tecnoandes.almacen.config;

import com.tecnoandes.almacen.model.Rol;
import com.tecnoandes.almacen.model.Usuario;
import com.tecnoandes.almacen.repository.RolRepository;
import com.tecnoandes.almacen.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Si no hay roles, los creamos
            if (rolRepo.count() == 0) {
                Rol adminRol = new Rol();
                adminRol.setNombre("ROLE_ADMIN");
                adminRol.setDescripcion("Administrador");
                rolRepo.save(adminRol);
            }

            // Si no existe el usuario admin, lo creamos
            if (usuarioRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456")); // Encripta la contraseña
                admin.setNombreCompleto("Administrador Demo");
                admin.setEmail("admin@tecnoandes.com");
                admin.setActivo(true);
                
                Rol rol = rolRepo.findAll().get(0); // Toma el primer rol
                admin.setRoles(Set.of(rol));
                
                usuarioRepo.save(admin);
                System.out.println(">>> Usuario 'admin' creado con contraseña '123456' <<<");
            }
        };
    }
}
