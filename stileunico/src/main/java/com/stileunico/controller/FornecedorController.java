package com.stileunico.controller;

import com.stileunico.DTO.request.FornecedorRequestDTO;
import com.stileunico.DTO.response.FornecedorResponseDTO;
import com.stileunico.mapper.FornecedorMapper;
import com.stileunico.model.Funcionario;
import com.stileunico.service.FornecedorService;
import com.stileunico.service.LogAcaoService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService service;
    private final LogAcaoService logService;

    public FornecedorController(FornecedorService service, LogAcaoService logService) {
        this.service = service;
        this.logService = logService;
    }

    // ---------------------- PEGAR FUNCIONÁRIO LOGADO ----------------------

    private Funcionario getFuncionarioLogado() {
        return (Funcionario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // ----------------------------- CREATE ----------------------------------

    @PostMapping
    public FornecedorResponseDTO cadastrar(@RequestBody FornecedorRequestDTO dto) {

        var fornecedor = service.salvar(FornecedorMapper.toEntity(dto));

        logService.registrar(
                getFuncionarioLogado(),
                "CADASTRAR FORNECEDOR",
                "Fornecedor cadastrado: " + fornecedor.getNome()
        );

        return FornecedorMapper.toResponse(fornecedor);
    }

    // ------------------------------ READ ALL --------------------------------

    @GetMapping
    public List<FornecedorResponseDTO> listar() {
        return service.listarTodos()
                .stream()
                .map(FornecedorMapper::toResponse)
                .toList();
    }

    // ------------------------------ READ ONE --------------------------------

    @GetMapping("/{id}")
    public FornecedorResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(FornecedorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
    }

    // ------------------------------ UPDATE ----------------------------------

    @PutMapping("/{id}")
    public FornecedorResponseDTO atualizar(@PathVariable Long id, @RequestBody FornecedorRequestDTO dto) {

        var fornecedorAtualizado = service.atualizar(id, FornecedorMapper.toEntity(dto))
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        logService.registrar(
                getFuncionarioLogado(),
                "ATUALIZAR FORNECEDOR",
                "Fornecedor atualizado: " + fornecedorAtualizado.getNome()
        );

        return FornecedorMapper.toResponse(fornecedorAtualizado);
    }

    // ------------------------------ DELETE ----------------------------------

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        boolean removido = service.deletar(id);

        if (!removido) {
            throw new RuntimeException("Fornecedor não encontrado");
        }

        logService.registrar(
                getFuncionarioLogado(),
                "DELETAR FORNECEDOR",
                "Fornecedor removido. ID: " + id
        );
    }
}
