package com.app.restaurante.controller;

import com.app.restaurante.dao.ReporteDAO;
import com.app.restaurante.service.ReporteService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportesController {

    @Autowired
    private ReporteDAO reporteDAO;

    @Autowired
    private ReporteService reporteService;

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

            case "auditoria_prod":
                return "admin/components/admin_report_auditoria_prod"; // nombre del fragmento

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

    // ==========================================================
    // 4. API → REPORTE DE VENTAS POR PRODUCTO / CATEGORÍA
    // ==========================================================
    @GetMapping("/admin/api/reporte/ventas")
    @ResponseBody
    public List<Map<String, Object>> reporteVentas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin,
            @RequestParam String atributo) {

        return reporteService.obtenerReporteVentas(inicio, fin, atributo);
    }

    // ==========================================================
    // 5. API → REPORTE ESPECÍFICO POR PRODUCTO
    // ==========================================================
    @GetMapping("/admin/api/productos")
    @ResponseBody
    public List<Map<String, Object>> obtenerProductos() {
        System.out.println("→ [CONTROLADOR] Solicitando lista de productos...");
        List<Map<String, Object>> lista = reporteService.listarProductos();
        System.out.println("→ [CONTROLADOR] Productos obtenidos: " + lista.size());
        lista.forEach(p -> System.out.println("  " + p));
        return lista;
    }

    // ==========================================================
    // 5. API → REPORTE ESPECÍFICO POR PRODUCTO (MES)
    // ==========================================================
    @GetMapping("/admin/api/reporte/producto")
    @ResponseBody
    public Map<String, Object> reporteProducto(
            @RequestParam int idProdHistoria,
            @RequestParam int anio,
            @RequestParam int mes) {

        System.out.println("→ [CONTROLADOR] Recibido idProdHistoria=" + idProdHistoria + ", año=" + anio + ", mes=" + mes);

        LocalDate inicioLD = LocalDate.of(anio, mes, 1);
        LocalDate finLD = inicioLD.withDayOfMonth(inicioLD.lengthOfMonth());

        Map<String, Object> data = new HashMap<>();

        List<Map<String, Object>> precios = reporteService.evolucionPrecios(idProdHistoria, inicioLD, finLD);
        List<Map<String, Object>> cantidades = reporteService.evolucionCantidades(idProdHistoria, inicioLD, finLD);
        List<Map<String, Object>> ventas = reporteService.ventasProducto(idProdHistoria, inicioLD, finLD);

        System.out.println("→ [CONTROLADOR] Precios obtenidos: " + precios.size());
        System.out.println("→ [CONTROLADOR] Cantidades obtenidas: " + cantidades.size());
        System.out.println("→ [CONTROLADOR] Ventas obtenidas: " + ventas.size());

        precios.forEach(p -> System.out.println("  Precio: " + p));
        cantidades.forEach(c -> System.out.println("  Cantidad: " + c));
        ventas.forEach(v -> System.out.println("  Venta: " + v));

        data.put("precios", precios);
        data.put("cantidades", cantidades);
        data.put("ventas", ventas);

        return data;
    }

    // ==========================================================
    // 6. API → Auditoría de Productos (LISTA)
    // ==========================================================
    @GetMapping("/admin/api/auditoria/producto/lista")
    @ResponseBody
    public List<Map<String, Object>> obtenerAuditoriaProductos() {
        return reporteDAO.listarProductos();
    }

    // ==========================================================
    // 7. API → Auditoría de Productos (BLOQUES)
    // ==========================================================
    @GetMapping("/admin/api/auditoria/producto/bloques")
    @ResponseBody
    public List<Map<String, Object>> auditoriaBloques(
            @RequestParam int idProducto,
            @RequestParam String tipo,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin) {

        LocalDate ini;
        LocalDate fn;

        if (!tipo.equalsIgnoreCase("rango")) {
            // Obtenemos el primer y último movimiento directamente como LocalDateTime
            Map<String, Object> rango = reporteDAO.obtenerPrimerUltimoMovimiento(idProducto);

            if (rango.get("inicio") == null || rango.get("fin") == null) {
                return List.of(); // no hay movimientos
            }

            // Convertimos LocalDateTime -> LocalDate
            ini = ((LocalDateTime) rango.get("inicio")).toLocalDate();
            fn = ((LocalDateTime) rango.get("fin")).toLocalDate();

        } else {
            // Rango personalizado desde frontend
            if (inicio == null || fin == null) {
                throw new RuntimeException("Debe enviar fechas para un rango personalizado");
            }
            ini = new java.sql.Date(inicio.getTime()).toLocalDate();
            fn = new java.sql.Date(fin.getTime()).toLocalDate();
        }

        // Generamos bloques según tipo
        List<Map<String, Object>> bloques = reporteService.generarBloquesAuditoria(ini, fn, tipo);

        // Para cada bloque, obtenemos los datos diarios
        for (Map<String, Object> b : bloques) {
            LocalDate i = LocalDate.parse(b.get("inicio").toString());
            LocalDate f = LocalDate.parse(b.get("fin").toString());
            List<Map<String, Object>> datos = reporteDAO.obtenerDatosDiariosAuditoria(idProducto, i, f);
            b.put("datos", datos);
        }

        return bloques;
    }

    // ==========================================================
    // 8. API → Auditoría de Productos (DETALLE BLOQUE)
    // ==========================================================
    @GetMapping("/admin/api/auditoria/producto/detalle")
    @ResponseBody
    public List<Map<String, Object>> auditoriaDetalle(
            @RequestParam int idProducto,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin) {

        return reporteDAO.obtenerDetalleAuditoria(idProducto, inicio, fin);
    }
}
