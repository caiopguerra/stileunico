package com.stileunico.controller;

import com.stileunico.DTO.request.FuncionarioRequestDTO;
import com.stileunico.DTO.response.FuncionarioResponseDTO;
import com.stileunico.mapper.FuncionarioMapper;
import com.stileunico.model.Funcionario;
import com.stileunico.service.FuncionarioService;
import com.stileunico.service.LogAcaoService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final LogAcaoService logService;

    public FuncionarioController(FuncionarioService funcionarioService,
                                 LogAcaoService logService) {
        this.funcionarioService = funcionarioService;
        this.logService = logService;
    }

    // ------------------------- FUNCIONÁRIO LOGADO --------------------------

    private Funcionario getFuncionarioLogado() {
        return (Funcionario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // ------------------------------ CREATE ----------------------------------

    @PostMapping
    public FuncionarioResponseDTO cadastrar(@RequestBody FuncionarioRequestDTO dto) {

        Funcionario novo = funcionarioService.cadastrarFuncionario(
                FuncionarioMapper.toEntity(dto)
        );

        logService.registrar(
                getFuncionarioLogado(),
                "CADASTRAR FUNCIONÁRIO",
                "Cadastrou o funcionário: " + novo.getNome()
        );

        return FuncionarioMapper.toResponse(novo);
    }

    // ------------------------------ READ ALL --------------------------------

    @GetMapping
    public List<FuncionarioResponseDTO> listar() {
        return funcionarioService.listarFuncionarios()
                .stream()
                .map(FuncionarioMapper::toResponse)
                .toList();
    }

    // ------------------------------ READ ONE --------------------------------

    @GetMapping("/{id}")
    public FuncionarioResponseDTO buscarPorId(@PathVariable Long id) {
        return FuncionarioMapper.toResponse(funcionarioService.buscarPorId(id));
    }

    // ------------------------------ UPDATE ----------------------------------

    @PutMapping("/{id}")
    public FuncionarioResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody FuncionarioRequestDTO dto) {

        Funcionario atualizado = funcionarioService.atualizarFuncionario(
                id,
                FuncionarioMapper.toEntity(dto)
        );

        logService.registrar(
                getFuncionarioLogado(),
                "ATUALIZAR FUNCIONÁRIO",
                "Atualizou o funcionário: " + atualizado.getNome()
        );

        return FuncionarioMapper.toResponse(atualizado);
    }

    // ------------------------------ DELETE ----------------------------------

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        Funcionario funcionarioRemovido = funcionarioService.buscarPorId(id);

        funcionarioService.deletarFuncionario(id);

        logService.registrar(
                getFuncionarioLogado(),
                "DELETAR FUNCIONÁRIO",
                "Removeu o funcionário: " + funcionarioRemovido.getNome()
        );
    }
}
