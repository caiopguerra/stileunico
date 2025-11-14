package com.stileunico.DTO.response;

import java.util.List;

public record ClienteResponseDTO(
        Long id,
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        List<EnderecoResponseDTO> enderecos
) {}
