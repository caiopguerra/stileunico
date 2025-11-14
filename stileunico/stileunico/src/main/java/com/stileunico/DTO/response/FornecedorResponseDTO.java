package com.stileunico.DTO.response;

public record FornecedorResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String telefone
) {}
