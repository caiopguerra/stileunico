package com.stileunico.DTO.request;

public record EstoqueRequestDTO(
        Long roupaId,
        String tamanho,
        int quantidade
) {}
