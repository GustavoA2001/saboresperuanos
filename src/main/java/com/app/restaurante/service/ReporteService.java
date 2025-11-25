package com.app.restaurante.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.restaurante.dao.ReporteDAO;

@Service
public class ReporteService {

    @Autowired
    private ReporteDAO reporteDAO;

    // =========================================================================
    // MÓDULO 1 — REPORTE GENERAL DEL SISTEMA
    // =========================================================================

    /**
     * Reporte General → Listado global por atributo (fecha, usuario, etc.)
     */
    public List<Map<String, Object>> obtenerReporteVentas(Date inicio, Date fin, String atributo) {
        return reporteDAO.reporteVentas(inicio, fin, atributo);
    }

    // =========================================================================
    // MÓDULO 2 — REPORTE POR PRODUCTO
    // =========================================================================

    /**
     * Reporte por Producto → Ventas por producto en un período.
     */
    public List<Map<String, Object>> obtenerVentasProducto(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.ventasProducto(idProducto, inicio, fin);
    }

    /**
     * Reporte por Producto → Listar productos para filtros.
     */
    public List<Map<String, Object>> listarProductos() {
        System.out.println("[SERVICE] Solicitando productos al DAO...");
        List<Map<String, Object>> lista = reporteDAO.listarProductos();
        System.out.println("[SERVICE] Productos recibidos: " + lista.size());
        return lista;
    }

    /**
     * Reporte por Producto → Evolución de precios en un periodo.
     */
    public List<Map<String, Object>> evolucionPrecios(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("[SERVICE] Solicitando evolución de precios para producto " + id);
        List<Map<String, Object>> lista = reporteDAO.evolucionPreciosProducto(id, inicio, fin);
        System.out.println("[SERVICE] Precios recibidos: " + lista.size());
        return lista;
    }

    /**
     * Reporte por Producto → Evolución de cantidades en un periodo.
     */
    public List<Map<String, Object>> evolucionCantidades(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("[SERVICE] Solicitando evolución de cantidades para producto " + id);
        List<Map<String, Object>> lista = reporteDAO.evolucionCantidadProducto(id, inicio, fin);
        System.out.println("[SERVICE] Cantidades recibidas: " + lista.size());
        return lista;
    }

    /**
     * Reporte por Producto → Ventas del producto en rango de fechas.
     */
    public List<Map<String, Object>> ventasProducto(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("[SERVICE] Solicitando ventas de producto " + id + " entre " + inicio + " y " + fin);
        List<Map<String, Object>> lista = reporteDAO.ventasProducto(id, inicio, fin);
        System.out.println("[SERVICE] Ventas recibidas: " + lista.size());
        return lista;
    }

    // =========================================================================
    // MÓDULO 3 — AUDITORÍA DE PRODUCTOS
    // =========================================================================

    /**
     * Auditoría → Primer movimiento y último movimiento del producto.
     */
    public Map<String, Object> obtenerPrimerUltimoMovimiento(int idProducto) {
        return reporteDAO.obtenerPrimerUltimoMovimiento(idProducto);
    }

    /**
     * Auditoría → Generar bloques de tiempo (semana, mes, año o rango manual).
     */
    public List<Map<String, Object>> generarBloquesAuditoria(LocalDate inicio, LocalDate fin, String tipo) {

        System.out.println("\n[Service] Generando bloques...");
        System.out.println("Tipo: " + tipo);
        System.out.println("Inicio: " + inicio + "   Fin: " + fin);

        List<Map<String, Object>> bloques = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate cursor = inicio;

        switch (tipo.toLowerCase()) {

            // ------------------------------------------------------
            // SEMANAS
            // ------------------------------------------------------
            case "semana":
                cursor = cursor.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
                System.out.println("Cursor alineado a lunes: " + cursor);

                while (!cursor.isAfter(fin)) {

                    LocalDate endWeek = cursor.plusDays(6);
                    if (endWeek.isAfter(fin))
                        endWeek = fin;

                    System.out.println(" - Semana: " + cursor + " → " + endWeek);

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endWeek.format(df));
                    b.put("titulo", "Semana " + cursor + " - " + endWeek);
                    bloques.add(b);

                    cursor = cursor.plusWeeks(1);
                }
                break;

            // ------------------------------------------------------
            // MESES
            // ------------------------------------------------------
            case "mes":
                cursor = LocalDate.of(inicio.getYear(), inicio.getMonthValue(), 1);
                System.out.println("Cursor ajustado al primer día del mes: " + cursor);

                while (!cursor.isAfter(fin)) {

                    LocalDate endMonth = cursor.withDayOfMonth(cursor.lengthOfMonth());
                    if (endMonth.isAfter(fin))
                        endMonth = fin;

                    String titulo = cursor.getMonth().getDisplayName(TextStyle.FULL, new Locale("es"))
                            + " " + cursor.getYear();

                    System.out.println(" - Mes: " + cursor + " → " + endMonth + "  |  Título: " + titulo);

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endMonth.format(df));
                    b.put("titulo", titulo);
                    bloques.add(b);

                    cursor = cursor.plusMonths(1).withDayOfMonth(1);
                }
                break;

            // ------------------------------------------------------
            // AÑOS
            // ------------------------------------------------------
            case "anio":
            case "año":
                cursor = cursor.withDayOfYear(1);
                System.out.println("Cursor ajustado al primer día del año: " + cursor);

                while (!cursor.isAfter(fin)) {

                    LocalDate endYear = cursor.withDayOfYear(cursor.lengthOfYear());
                    if (endYear.isAfter(fin))
                        endYear = fin;

                    System.out.println(" - Año: " + cursor + " → " + endYear);

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endYear.format(df));
                    b.put("titulo", "Año " + cursor.getYear());
                    bloques.add(b);

                    cursor = cursor.plusYears(1).withDayOfYear(1);
                }
                break;

            // ------------------------------------------------------
            // RANGO MANUAL
            // ------------------------------------------------------
            default:
                System.out.println(" - RANGO DIRECTO: " + inicio + " → " + fin);

                Map<String, Object> b = new HashMap<>();
                b.put("inicio", inicio.format(df));
                b.put("fin", fin.format(df));
                b.put("titulo", "Rango: " + inicio + " - " + fin);
                bloques.add(b);
        }

        System.out.println("Total bloques creados: " + bloques.size());
        return bloques;
    }

    /**
     * Auditoría → Obtener valores diarios resumidos.
     */
    public List<Map<String, Object>> obtenerDatosDiariosAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.obtenerDatosDiariosAuditoria(idProducto, inicio, fin);
    }

    /**
     * Auditoría → Obtener detalle de movimientos completos del producto.
     */
    public List<Map<String, Object>> obtenerDetalleAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.obtenerDetalleAuditoria(idProducto, inicio, fin);
    }
}
