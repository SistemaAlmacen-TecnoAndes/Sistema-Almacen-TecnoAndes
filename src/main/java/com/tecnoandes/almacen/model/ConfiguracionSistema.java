package com.tecnoandes.almacen.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuracion_sistema")
public class ConfiguracionSistema {

    @Id
    private Long id;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false, length = 11)
    private String ruc;

    @Column(name = "correo_soporte")
    private String correoSoporte;

    @Column(nullable = false)
    private String moneda;

    @Column(name = "registros_por_pagina", nullable = false)
    private Integer registrosPorPagina;

    @Column(name = "stock_minimo_defecto", nullable = false)
    private Integer stockMinimoDefecto;

    @Column(name = "permitir_stock_negativo", nullable = false)
    private Boolean permitirStockNegativo;

    @Column(name = "prefijo_producto", nullable = false)
    private String prefijoProducto;

    @Column(name = "tiempo_sesion_minutos", nullable = false)
    private Integer tiempoSesionMinutos;

    @Column(name = "min_longitud_password", nullable = false)
    private Integer minLongitudPassword;

    @Column(name = "ultima_modificacion")
    private LocalDateTime ultimaModificacion;

    @Column(name = "modificado_por")
    private String modificadoPor;

    // Getters y Setters (Genera los tuyos o pégalos aquí)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getCorreoSoporte() { return correoSoporte; }
    public void setCorreoSoporte(String correoSoporte) { this.correoSoporte = correoSoporte; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public Integer getRegistrosPorPagina() { return registrosPorPagina; }
    public void setRegistrosPorPagina(Integer registrosPorPagina) { this.registrosPorPagina = registrosPorPagina; }
    public Integer getStockMinimoDefecto() { return stockMinimoDefecto; }
    public void setStockMinimoDefecto(Integer stockMinimoDefecto) { this.stockMinimoDefecto = stockMinimoDefecto; }
    public Boolean getPermitirStockNegativo() { return permitirStockNegativo; }
    public void setPermitirStockNegativo(Boolean permitirStockNegativo) { this.permitirStockNegativo = permitirStockNegativo; }
    public String getPrefijoProducto() { return prefijoProducto; }
    public void setPrefijoProducto(String prefijoProducto) { this.prefijoProducto = prefijoProducto; }
    public Integer getTiempoSesionMinutos() { return tiempoSesionMinutos; }
    public void setTiempoSesionMinutos(Integer tiempoSesionMinutos) { this.tiempoSesionMinutos = tiempoSesionMinutos; }
    public Integer getMinLongitudPassword() { return minLongitudPassword; }
    public void setMinLongitudPassword(Integer minLongitudPassword) { this.minLongitudPassword = minLongitudPassword; }
    public LocalDateTime getUltimaModificacion() { return ultimaModificacion; }
    public void setUltimaModificacion(LocalDateTime ultimaModificacion) { this.ultimaModificacion = ultimaModificacion; }
    public String getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(String modificadoPor) { this.modificadoPor = modificadoPor; }
}