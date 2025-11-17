package com.app.restaurante.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public List<Map<String, Object>> obtenerReporteVentas(Date inicio, Date fin, String atributo) {
        return reporteDAO.reporteVentas(inicio, fin, atributo);
    }

    public List<Map<String, Object>> obtenerVentasProducto(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.ventasProducto(idProducto, inicio, fin);
    }

    public Map<String, Object> obtenerPrimerUltimoMovimiento(int idProducto) {
        return reporteDAO.obtenerPrimerUltimoMovimiento(idProducto);
    }

    public List<Map<String, Object>> generarBloquesAuditoria(LocalDate inicio, LocalDate fin, String tipo) {
        List<Map<String, Object>> bloques = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate cursor = inicio;

        switch (tipo.toLowerCase()) {
            case "semana":
                cursor = cursor.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
                while (!cursor.isAfter(fin)) {
                    LocalDate endWeek = cursor.plusDays(6);
                    if (endWeek.isAfter(fin))
                        endWeek = fin;

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endWeek.format(df));
                    b.put("titulo", "Semana " + cursor.format(df) + " → " + endWeek.format(df));
                    bloques.add(b);

                    cursor = cursor.plusWeeks(1);
                }
                break;

            case "mes":
                cursor = cursor.withDayOfMonth(1);
                while (!cursor.isAfter(fin)) {
                    LocalDate endMonth = cursor.withDayOfMonth(cursor.lengthOfMonth());
                    if (endMonth.isAfter(fin))
                        endMonth = fin;

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endMonth.format(df));
                    b.put("titulo", cursor.getMonth().toString() + " " + cursor.getYear());
                    bloques.add(b);

                    cursor = cursor.plusMonths(1).withDayOfMonth(1);
                }
                break;

            case "anio":
            case "año":
                cursor = cursor.withDayOfYear(1);
                while (!cursor.isAfter(fin)) {
                    LocalDate endYear = cursor.withDayOfYear(cursor.lengthOfYear());
                    if (endYear.isAfter(fin))
                        endYear = fin;

                    Map<String, Object> b = new HashMap<>();
                    b.put("inicio", cursor.format(df));
                    b.put("fin", endYear.format(df));
                    b.put("titulo", "Año " + cursor.getYear());
                    bloques.add(b);

                    cursor = cursor.plusYears(1).withDayOfYear(1);
                }
                break;

            default: // rango
                Map<String, Object> b = new HashMap<>();
                b.put("inicio", inicio.format(df));
                b.put("fin", fin.format(df));
                b.put("titulo", "Rango: " + inicio.format(df) + " → " + fin.format(df));
                bloques.add(b);
        }

        return bloques;
    }

    public List<Map<String, Object>> obtenerDatosDiariosAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.obtenerDatosDiariosAuditoria(idProducto, inicio, fin);
    }

    public List<Map<String, Object>> obtenerDetalleAuditoria(int idProducto, LocalDate inicio, LocalDate fin) {
        return reporteDAO.obtenerDetalleAuditoria(idProducto, inicio, fin);
    }

    public List<Map<String, Object>> listarProductos() {
        System.out.println("→ [SERVICE] Solicitando productos al DAO...");
        List<Map<String, Object>> lista = reporteDAO.listarProductos();
        System.out.println("→ [SERVICE] Productos recibidos: " + lista.size());
        return lista;
    }
    public List<Map<String, Object>> evolucionPrecios(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("→ [SERVICE] Solicitando evolución de precios para producto " + id);
        List<Map<String, Object>> lista = reporteDAO.evolucionPreciosProducto(id, inicio, fin);
        System.out.println("→ [SERVICE] Precios recibidos: " + lista.size());
        return lista;
    }
    public List<Map<String, Object>> evolucionCantidades(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("→ [SERVICE] Solicitando evolución de cantidades para producto " + id);
        List<Map<String, Object>> lista = reporteDAO.evolucionCantidadProducto(id, inicio, fin);
        System.out.println("→ [SERVICE] Cantidades recibidas: " + lista.size());
        return lista;
    }
    public List<Map<String, Object>> ventasProducto(int id, LocalDate inicio, LocalDate fin) {
        System.out.println("→ [SERVICE] Solicitando ventas de producto " + id + " entre " + inicio + " y " + fin);
        List<Map<String, Object>> lista = reporteDAO.ventasProducto(id, inicio, fin);
        System.out.println("→ [SERVICE] Ventas recibidas: " + lista.size());
        return lista;
    }
}
