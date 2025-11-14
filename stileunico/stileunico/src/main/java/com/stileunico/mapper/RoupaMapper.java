package com.stileunico.mapper;

import com.stileunico.DTO.request.RoupaRequestDTO;
import com.stileunico.DTO.response.RoupaResponseDTO;
import com.stileunico.model.Roupa;

public class RoupaMapper {

    public static Roupa toEntity(RoupaRequestDTO dto) {
        Roupa r = new Roupa();
        r.setNome(dto.nome());
        r.setDescricao(dto.descricao());
        r.setPreco(dto.preco());
        return r;
    }

    public static RoupaResponseDTO toResponse(Roupa entity) {
        return new RoupaResponseDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getPreco()
        );
    }
}
