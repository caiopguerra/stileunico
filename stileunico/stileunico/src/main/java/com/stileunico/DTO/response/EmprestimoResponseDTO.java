package com.stileunico.DTO.response;

import java.time.LocalDate;

public record EmprestimoResponseDTO(
        Long id,
        Long clienteId,
        Long roupaId,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao,
        boolean devolvido
) {}