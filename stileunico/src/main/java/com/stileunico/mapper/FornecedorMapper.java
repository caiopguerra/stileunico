package com.stileunico.mapper;

import com.stileunico.DTO.request.FornecedorRequestDTO;
import com.stileunico.DTO.response.FornecedorResponseDTO;
import com.stileunico.model.Fornecedor;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorRequestDTO dto) {
        Fornecedor f = new Fornecedor();
        f.setNome(dto.nome());
        f.setCnpj(dto.cnpj());
        f.setTelefone(dto.telefone());
        return f;
    }

    public static FornecedorResponseDTO toResponse(Fornecedor entity) {
        return new FornecedorResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getTelefone()
        );
    }
}
