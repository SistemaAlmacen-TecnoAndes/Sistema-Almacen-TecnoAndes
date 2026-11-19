package com.tecnoandes.almacen.controller;

import com.tecnoandes.almacen.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Mostrar la vista de reportes
    @GetMapping
    public String mostrarVistaReportes() {
        return "reportes/r_index";
    }

    // Descargar Excel
    @GetMapping("/inventario/excel")
    public ResponseEntity<InputStreamResource> descargarExcel() {
        ByteArrayInputStream stream = reporteService.exportarInventarioExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=inventario.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }

    // Descargar PDF
    @GetMapping("/inventario/pdf")
    public ResponseEntity<InputStreamResource> descargarPdf() {
        ByteArrayInputStream stream = reporteService.exportarInventarioPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=inventario.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }
}