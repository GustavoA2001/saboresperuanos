package com.app.restaurante.controller;

import com.app.restaurante.dao.ReporteDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportesController {

    @Autowired
    private ReporteDAO reporteDAO;

    // ==========================================================
    // 1. VISTA PRINCIPAL DE REPORTES
    // ==========================================================
    @GetMapping("/admin/reportes")
    public String reportesGenerales(Model model) {
        model.addAttribute("activeSection", "reporte");
        return "admin/admin_reporte";
    }

    // ==========================================================
    // 2. CARGA DE COMPONENTES
    // ==========================================================
    @GetMapping("/admin/reporte/{tipo}")
    public String cargarComponente(@PathVariable String tipo) {

        switch (tipo) {

            case "general":
                return "admin/components/admin_report_general";

            case "ventas":
                return "admin/components/admin_report_ventas";

            case "productos":
                return "admin/components/admin_report_productos";

            case "clientes":
                return "admin/components/admin_report_clientes";

            case "metodos":
                return "admin/components/admin_report_metodos";

            case "ingresos":
                return "admin/components/admin_report_ingresos";

            case "movimientos":
                return "admin/components/admin_report_movimientos";

            default:
                return "admin/components/admin_report_notfound";
        }
    }

    // ==========================================================
    // 3. API → REPORTE GENERAL
    // ==========================================================
    @GetMapping("/admin/api/reporte/general")
    @ResponseBody
    public Map<String, Object> reporteGeneral(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin) {

        Map<String, Object> data = new HashMap<>();

        data.put("ventasTotales", reporteDAO.obtenerVentasTotales(inicio, fin));
        data.put("productosVendidos", reporteDAO.obtenerCantidadProductosVendidos(inicio, fin));
        data.put("categorias", reporteDAO.obtenerCategoriasMasVendidas(inicio, fin));
        data.put("clientesFrecuentes", reporteDAO.obtenerClientesFrecuentes(inicio, fin));
        data.put("evolucion", reporteDAO.obtenerEvolucion(inicio, fin));
        data.put("detalle", reporteDAO.obtenerDetalleReporte(inicio, fin));

        return data;
    }

    // ==========================================================
    // 3. EXPORTAR → PDF
    // ==========================================================
    @GetMapping("/admin/reporte/general/pdf")
    public void exportarPDF(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin,
            HttpServletResponse response) throws IOException {


        List<Map<String, Object>> detalle = reporteDAO.obtenerDetalleReporte(inicio, fin);
        List<Map<String, Object>> evolucion = reporteDAO.obtenerEvolucion(inicio, fin);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_general.pdf");

        try {
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);

            Paragraph titulo = new Paragraph("Reporte General del Sistema\n\n", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            doc.add(new Paragraph("Fechas: " + inicio + "  →  " + fin));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("Evolución de ventas"));
            doc.add(new Paragraph("\n"));

            // TABLA EVOLUCIÓN
            PdfPTable tablaEvo = new PdfPTable(2);
            tablaEvo.setWidthPercentage(100);

            tablaEvo.addCell("Fecha");
            tablaEvo.addCell("Total");

            for (Map<String, Object> row : evolucion) {
                tablaEvo.addCell(row.get("fecha").toString());
                tablaEvo.addCell(row.get("total").toString());
            }

            doc.add(tablaEvo);
            doc.add(new Paragraph("\n\nDetalle de pedidos:"));
            doc.add(new Paragraph("\n"));

            // TABLA DETALLE
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            tabla.addCell("Fecha");
            tabla.addCell("Detalle");
            tabla.addCell("Movimientos");
            tabla.addCell("Total");

            for (Map<String, Object> row : detalle) {
                tabla.addCell(row.get("FechaPedido").toString());
                tabla.addCell(row.get("detalle").toString());
                tabla.addCell(row.get("movimientos").toString());
                tabla.addCell("S/. " + row.get("total").toString());
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
