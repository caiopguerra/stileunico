package com.stileunico.model;

import jakarta.persistence.Entity;


@Entity
public class Admin extends Funcionario {

    public  Admin(){
        
    }


    public Admin(String nome, String email, String senha, String cpf, Role cargo, String rg, String cargaHoraria, Double salario,
    Endereco endereco, String telefone) {
        super(nome, email, senha, cpf, cargo, rg, cargaHoraria, salario, endereco, telefone);
    }

}
