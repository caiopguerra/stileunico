package com.stileunico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Roupa extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cor;

    private String tipo;

    private String tamanho;

    private Double preco;

    private String descricao;

    private String fotos;

    private String marca;

    private Boolean oferta = false;

    private Integer estoque = 0;

    private Integer vendas = 0;

    @ManyToOne
    @JoinColumn(name = "tipo_roupa_id")
    private TipoRoupa tipoRoupa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Boolean getOferta() {
        return oferta;
    }

    public void setOferta(Boolean oferta) {
        this.oferta = oferta;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Integer getVendas() {
        return vendas;
    }

    public void setVendas(Integer vendas) {
        this.vendas = vendas;
    }

    public TipoRoupa getTipoRoupa() {
    return tipoRoupa;
}

public void setTipoRoupa(TipoRoupa tipoRoupa) {
    this.tipoRoupa = tipoRoupa;
}


    public Roupa() {
    }

    // Construtor
    public Roupa(String nome, String cor, String tipo, String tamanho, Double preco, String descricao, String fotos,
            String marca, Boolean oferta, Integer estoque) {
        this.nome = nome;
        this.cor = cor;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.preco = preco;
        this.descricao = descricao;
        this.fotos = fotos;
        this.marca = marca;
        this.oferta = oferta;
        this.estoque = estoque;
    }

}