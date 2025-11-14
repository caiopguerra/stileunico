package com.stileunico.DTO.request;

import java.time.LocalDate;

public record EmprestimoRequestDTO(
        Long clienteId,
        Long roupaId,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao) {
}