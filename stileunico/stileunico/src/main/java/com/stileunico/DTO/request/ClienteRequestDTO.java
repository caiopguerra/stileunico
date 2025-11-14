package com.stileunico.DTO.request;
import com.stileunico.DTO.request.EnderecoRequestDTO;

import java.util.List;

public record ClienteRequestDTO(
        String nomeCompleto,
        String cpf,
        String email,
        String telefone,
        List<EnderecoRequestDTO> enderecos
) {}