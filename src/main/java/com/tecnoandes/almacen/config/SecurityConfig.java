package com.tecnoandes.almacen.config;

import com.tecnoandes.almacen.model.Usuario;
import com.tecnoandes.almacen.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/login").permitAll()
                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    // Si inicia sesión bien, reseteamos los intentos
                    String username = authentication.getName();
                    Usuario user = usuarioRepository.findByUsername(username).orElse(null);
                    if (user != null) {
                        user.setIntentosFallidos(0);
                        user.setBloqueadoHasta(null);
                        usuarioRepository.save(user);
                    }
                    response.sendRedirect("/dashboard");
                })
                .failureHandler((request, response, exception) -> {
                    // Si falla, sumamos 1 intento
                    String username = request.getParameter("username");
                    Usuario user = usuarioRepository.findByUsername(username).orElse(null);
                    
                    if (user != null) {
                        int intentos = user.getIntentosFallidos() + 1;
                        user.setIntentosFallidos(intentos);
                        
                        if (intentos >= 3) {
                            // Bloquear por 15 segundos
                            user.setBloqueadoHasta(LocalDateTime.now().plusSeconds(15));
                            user.setIntentosFallidos(0); // Reseteamos el contador para el próximo bloqueo
                        }
                        usuarioRepository.save(user);
                    }
                    // Redirige al login con error (o con parámetro de bloqueado)
                    response.sendRedirect("/login?error");
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
}
