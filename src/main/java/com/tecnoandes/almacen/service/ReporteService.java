package com.tecnoandes.almacen.service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tecnoandes.almacen.model.Producto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ProductoService productoService;

    // Obtener la fecha y hora actual formateada
    private String obtenerFechaHora() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return ahora.format(formato);
    }

    // ==========================================
    // REPORTE EXCEL MEJORADO (Apache POI)
    // ==========================================
    public ByteArrayInputStream exportarInventarioExcel() {
        List<Producto> productos = productoService.listarTodos();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Inventario");

            // --- ESTILOS ---
            // Título principal
            CellStyle titleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Subtítulo (Fecha)
            CellStyle subtitleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font subFont = workbook.createFont();
            subFont.setItalic(true);
            subFont.setFontHeightInPoints((short) 10);
            subtitleStyle.setFont(subFont);
            subtitleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Cabecera de tabla
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            // --- DATOS DEL ENCABEZADO ---
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("REPORTE GENERAL DE INVENTARIO - TECNOANDES PERÚ S.A.C.");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            Row subtitleRow = sheet.createRow(1);
            Cell subtitleCell = subtitleRow.createCell(0);
            subtitleCell.setCellValue("Fecha de generación: " + obtenerFechaHora());
            subtitleCell.setCellStyle(subtitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

            // --- CABECERA TABLA ---
            Row headerRow = sheet.createRow(3);
            String[] columns = {"Código", "Nombre del Producto", "Categoría", "Stock", "Precio Venta (S/)", "Estado"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- LLENAR DATOS ---
            int rowIdx = 4;
            for (Producto prod : productos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(prod.getCodigo());
                row.createCell(1).setCellValue(prod.getNombre());
                row.createCell(2).setCellValue(prod.getCategoria().getNombre());
                row.createCell(3).setCellValue(prod.getStock());
                row.createCell(4).setCellValue(prod.getPrecioVenta().doubleValue());
                
                String estado = prod.getStock() <= prod.getStockMinimo() ? "STOCK BAJO" : "NORMAL";
                row.createCell(5).setCellValue(estado);
            }

            // Autoajustar tamaño de columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error al generar Excel: " + e.getMessage());
        }
    }

    // ==========================================
    // REPORTE PDF MEJORADO (OpenPDF)
    // ==========================================
    public ByteArrayInputStream exportarInventarioPdf() {
        List<Producto> productos = productoService.listarTodos();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- TÍTULOS ---
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(30, 60, 114));
            Paragraph title = new Paragraph("REPORTE GENERAL DE INVENTARIO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph subtitle = new Paragraph("TecnoAndes Perú S.A.C.", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            Paragraph fecha = new Paragraph("Fecha de generación: " + obtenerFechaHora(), subtitleFont);
            fecha.setAlignment(Element.ALIGN_CENTER);
            fecha.setSpacingAfter(20);
            document.add(fecha);

            // --- TABLA ---
            PdfPTable table = new PdfPTable(6); // 6 columnas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 3f, 2f, 1f, 1.5f, 1.5f}); // Proporciones de ancho

            // Estilo de cabecera
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Color headerColor = new Color(30, 60, 114); // Azul oscuro

            String[] headers = {"Código", "Producto", "Categoría", "Stock", "Precio (S/)", "Estado"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            // Estilo de celdas de datos
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            for (Producto prod : productos) {
                table.addCell(new PdfPCell(new Phrase(prod.getCodigo(), dataFont)));
                table.addCell(new PdfPCell(new Phrase(prod.getNombre(), dataFont)));
                table.addCell(new PdfPCell(new Phrase(prod.getCategoria().getNombre(), dataFont)));
                
                PdfPCell stockCell = new PdfPCell(new Phrase(String.valueOf(prod.getStock()), dataFont));
                stockCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(stockCell);
                
                PdfPCell precioCell = new PdfPCell(new Phrase("S/ " + prod.getPrecioVenta().toString(), dataFont));
                precioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(precioCell);

                String estadoStr = prod.getStock() <= prod.getStockMinimo() ? "STOCK BAJO" : "NORMAL";
                PdfPCell estadoCell = new PdfPCell(new Phrase(estadoStr, dataFont));
                estadoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if (estadoStr.equals("STOCK BAJO")) {
                    estadoCell.setBackgroundColor(new Color(255, 204, 204)); // Rojo claro
                }
                table.addCell(estadoCell);
            }

            document.add(table);
            
            // --- PIE DE PÁGINA ---
            Paragraph footer = new Paragraph("Documento generado automáticamente por TecnoAndés Perú S.A.C", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}