package com.tecnoandes.almacen.service;

import com.tecnoandes.almacen.model.ConfiguracionSistema;
import com.tecnoandes.almacen.repository.ConfiguracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfiguracionService {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    // Siempre obtenemos el registro con ID 1
    public ConfiguracionSistema obtenerConfiguracion() {
        return configuracionRepository.findById(1L).orElse(new ConfiguracionSistema());
    }

    public void actualizarConfiguracion(ConfiguracionSistema nuevaConfig, String usuario) {
        nuevaConfig.setId(1L); // Forzamos el ID 1
        nuevaConfig.setUltimaModificacion(LocalDateTime.now());
        nuevaConfig.setModificadoPor(usuario);
        configuracionRepository.save(nuevaConfig);
    }
}
