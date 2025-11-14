package com.stileunico.DTO.response;

import java.time.LocalDateTime;

public record LogAcaoResponseDTO(
        Long id,
        String acao,
        String usuario,
        LocalDateTime dataHora) {
}
