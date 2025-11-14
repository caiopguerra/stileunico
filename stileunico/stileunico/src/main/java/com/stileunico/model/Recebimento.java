package com.stileunico.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Recebimento extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String notaFiscal;
    private LocalDate data;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "recebimento", cascade = CascadeType.ALL)
    private List<ItemRecebimento> itens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<ItemRecebimento> getItens() {
        return itens;
    }

    public void setItens(List<ItemRecebimento> itens) {
        this.itens = itens;
    }

    public Recebimento() {}

    
}
