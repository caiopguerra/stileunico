package com.stileunico.DTO.response;

import com.stileunico.model.Role;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String rg,
        String cargoHoraria,
        double salario,
        Role cargo
) {}
