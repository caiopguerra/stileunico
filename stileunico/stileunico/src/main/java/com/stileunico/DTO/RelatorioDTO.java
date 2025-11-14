package com.stileunico.DTO;

import java.math.BigDecimal;
import java.util.List;


public class RelatorioDTO {

    private BigDecimal faturamento;

    private int totalRoupasVendidas;

    private List<String> roupasMaisVendidas;

    private String filtroAplicado;

    private int totalFuncionarios; // usado em relatórios de funcionários

    private String titulo;

public BigDecimal getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(BigDecimal faturamento) {
        this.faturamento = faturamento;
    }

    public int getTotalRoupasVendidas() {
        return totalRoupasVendidas;
    }

    public void setTotalRoupasVendidas(int totalRoupasVendidas) {
        this.totalRoupasVendidas = totalRoupasVendidas;
    }

    public List<String> getRoupasMaisVendidas() {
        return roupasMaisVendidas;
    }

    public void setRoupasMaisVendidas(List<String> roupasMaisVendidas) {
        this.roupasMaisVendidas = roupasMaisVendidas;
    }

    public String getFiltroAplicado() {
        return filtroAplicado;
    }

    public void setFiltroAplicado(String filtroAplicado) {
        this.filtroAplicado = filtroAplicado;
    }

    public int getTotalFuncionarios() {
        return totalFuncionarios;
    }

    public void setTotalFuncionarios(int totalFuncionarios) {
        this.totalFuncionarios = totalFuncionarios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Construtores
    public RelatorioDTO() 
    {}

    public RelatorioDTO(BigDecimal faturamento, int totalRoupasVendidas, List<String> roupasMaisVendidas, String titulo, String filtroAplicado, int totalFuncionarios) {
        this.faturamento = faturamento;
        this.totalRoupasVendidas = totalRoupasVendidas;
        this.roupasMaisVendidas = roupasMaisVendidas;
        this.filtroAplicado = filtroAplicado;
        this.totalFuncionarios = totalFuncionarios;
        this.titulo = titulo;
    }

}