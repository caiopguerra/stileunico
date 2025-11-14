package com.stileunico.controller;

import com.stileunico.DTO.request.EstoqueRequestDTO;
import com.stileunico.DTO.response.EstoqueResponseDTO;
import com.stileunico.mapper.EstoqueMapper;
import com.stileunico.model.Funcionario;
import com.stileunico.service.EstoqueService;
import com.stileunico.service.LogAcaoService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService service;
    private final LogAcaoService logService;

    public EstoqueController(EstoqueService service, LogAcaoService logService) {
        this.service = service;
        this.logService = logService;
    }

    // ---------------------- UTIL PARA PEGAR FUNC LOGADO ----------------------

    private Funcionario getFuncionarioLogado() {
        return (Funcionario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // ----------------------------- ENDPOINT ----------------------------------

    @PostMapping
    public EstoqueResponseDTO adicionar(@RequestBody EstoqueRequestDTO dto) {

        var estoque = service.adicionar(
                dto.roupaId(),
                dto.tamanho(),
                dto.quantidade()
        );

        // ------- LOG -------
        logService.registrar(
                getFuncionarioLogado(),
                "ALTERAR ESTOQUE",
                "Adicionado " + dto.quantidade() +
                        " unid. de tamanho " + dto.tamanho() +
                        " para a roupa ID: " + dto.roupaId()
        );

        return EstoqueMapper.toResponse(estoque);
    }
}
