package com.stileunico.controller;

import com.stileunico.DTO.request.ClienteRequestDTO;
import com.stileunico.DTO.response.ClienteResponseDTO;
import com.stileunico.mapper.ClienteMapper;
import com.stileunico.model.Funcionario;
import com.stileunico.service.ClienteService;
import com.stileunico.service.LogAcaoService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;
    private final LogAcaoService logService;

    public ClienteController(ClienteService service, LogAcaoService logService) {
        this.service = service;
        this.logService = logService;
    }

    // -------------------- UTIL PARA PEGAR FUNCIONÁRIO LOGADO --------------------

    private Funcionario getFuncionarioLogado() {
        return (Funcionario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // -------------------- ENDPOINTS --------------------

    @PostMapping
    public ClienteResponseDTO cadastrar(@RequestBody ClienteRequestDTO dto) {

        var cliente = service.save(ClienteMapper.toEntity(dto));

        // LOG
        logService.registrar(
                getFuncionarioLogado(),
                "CADASTRAR CLIENTE",
                "Cliente criado: " + cliente.getEmail()
        );

        return ClienteMapper.toResponse(cliente);
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {

        // LOG
        logService.registrar(
                getFuncionarioLogado(),
                "LISTAR CLIENTES",
                "Listagem de clientes realizada."
        );

        return service.findAll()
                .stream()
                .map(ClienteMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscar(@PathVariable Long id) {

        var cliente = service.findById(id);

        // LOG
        logService.registrar(
                getFuncionarioLogado(),
                "CONSULTAR CLIENTE",
                "Consultado cliente ID: " + id
        );

        return ClienteMapper.toResponse(cliente);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {

        var atualizado = service.update(id, ClienteMapper.toEntity(dto));

        // LOG
        logService.registrar(
                getFuncionarioLogado(),
                "ATUALIZAR CLIENTE",
                "Atualizado cliente ID: " + id
        );

        return ClienteMapper.toResponse(atualizado);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {

        service.deleteById(id);

        // LOG
        logService.registrar(
                getFuncionarioLogado(),
                "DELETAR CLIENTE",
                "Cliente removido ID: " + id
        );
    }
}
