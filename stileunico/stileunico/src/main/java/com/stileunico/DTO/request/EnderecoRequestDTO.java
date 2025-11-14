package com.stileunico.DTO.request;

public record EnderecoRequestDTO(
        String rua,
        String numero,
        String cidade,
        String estado,
        String cep,
        String bairro
) {}