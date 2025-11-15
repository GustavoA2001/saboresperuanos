package com.app.restaurante.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReportDTO {
    
    public static class SummaryDTO {
        public BigDecimal totalVentas;
        public Long productosVendidos;
        public Long clientesAtendidos;
        public Long comprobantesEmitidos;
    }

    public static class TimePointDTO {
        public String label; // ej "2025-11-01" o "14:00"
        public BigDecimal value;
    }

    public static class TableRowDTO {
        public Map<String, Object> row; // flexible: frontend leerá keys
    }

    public static class ReportResponse {
        public SummaryDTO summary;
        public List<TimePointDTO> timeseries;
        public List<TableRowDTO> table;
    }
}
