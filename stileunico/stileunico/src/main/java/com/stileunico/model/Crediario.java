package com.stileunico.model;

import jakarta.persistence.*;


@Entity
public class Crediario extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beneficio;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Crediario() {}

    public Crediario(String beneficio, Cliente cliente) {
        this.beneficio = beneficio;
        this.cliente = cliente;
    }

   

}