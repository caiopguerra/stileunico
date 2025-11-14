package com.stileunico.mapper;

import com.stileunico.DTO.request.EstoqueRequestDTO;
import com.stileunico.DTO.response.EstoqueResponseDTO;
import com.stileunico.model.Estoque;
import com.stileunico.model.Roupa;

public class EstoqueMapper {

    public static Estoque toEntity(EstoqueRequestDTO dto) {
        Estoque e = new Estoque();
        Roupa r = new Roupa();
        r.setId(dto.roupaId());
        e.setRoupa(r);
        e.setTamanho(dto.tamanho());
        e.setQuantidade(dto.quantidade());
        return e;
    }

    public static EstoqueResponseDTO toResponse(Estoque entity) {
        return new EstoqueResponseDTO(
            entity.getId(),
            entity.getRoupa().getId(),
            entity.getTamanho(),
            entity.getQuantidade()
        );
    }
}
