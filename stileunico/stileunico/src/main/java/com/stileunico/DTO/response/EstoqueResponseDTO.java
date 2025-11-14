package com.stileunico.DTO.response;

public record EstoqueResponseDTO(
        Long id,
        Long roupaId,
        String tamanho,
        int quantidade
) {}