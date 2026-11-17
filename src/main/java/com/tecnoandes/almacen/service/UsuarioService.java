package com.tecnoandes.almacen.service;

import com.tecnoandes.almacen.model.Rol;
import com.tecnoandes.almacen.model.Usuario;
import com.tecnoandes.almacen.repository.RolRepository;
import com.tecnoandes.almacen.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    // Método especial para guardar con contraseña encriptada
    public Usuario guardar(Usuario usuario) {
        // Si el ID es nulo, es un usuario nuevo (encriptamos la contraseña)
        if (usuario.getId() == null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Si ya existe, buscamos la contraseña anterior para no sobreescribirla con texto plano
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                usuario.setPassword(usuarioExistente.getPassword());
            }
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
