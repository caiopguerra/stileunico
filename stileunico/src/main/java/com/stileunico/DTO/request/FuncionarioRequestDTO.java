package com.stileunico.DTO.request;

import com.stileunico.model.Role;

public record FuncionarioRequestDTO(
        String nome,
        String email,
        String senha,
        String cpf,
        String rg,
        String cargoHoraria,
        double salario,
        Role cargo
) {}
