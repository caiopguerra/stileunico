package com.stileunico.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.stileunico.DTO.RelatorioDTO;
import com.stileunico.model.Funcionario;
import com.stileunico.service.AdminService;
import com.stileunico.service.FuncionarioService;
import com.stileunico.service.LogAcaoService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final FuncionarioService funcionarioService;
    private final LogAcaoService logService;

    public AdminController(AdminService adminService,
                           FuncionarioService funcionarioService,
                           LogAcaoService logService) {
        this.adminService = adminService;
        this.funcionarioService = funcionarioService;
        this.logService = logService;
    }

    // 🔎 Método para pegar o funcionário logado via token
    private Funcionario getFuncionarioLogado(Authentication auth) {
        String email = auth.getName();
        return funcionarioService.listarFuncionarios().stream()
                .filter(f -> f.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // ------------------------ FUNCIONÁRIOS ------------------------

    @PostMapping("/funcionarios")
    public ResponseEntity<Funcionario> cadastrarFuncionario(
            @RequestBody Funcionario funcionario,
            Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        Funcionario saved = adminService.cadastrarFuncionario(funcionario);

        logService.registrar(
                logado,
                "CADASTRAR FUNCIONÁRIO",
                "Funcionário cadastrado: " + saved.getNome()
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> consultarFuncionarios(Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        logService.registrar(
                logado,
                "CONSULTAR FUNCIONÁRIOS",
                "Listagem de funcionários"
        );

        return ResponseEntity.ok(adminService.consultarFuncionarios());
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Funcionario> alterarFuncionario(
            @PathVariable Long id,
            @RequestBody Funcionario funcionario,
            Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        Funcionario atualizado = adminService.alterarFuncionario(id, funcionario);

        logService.registrar(
                logado,
                "ALTERAR FUNCIONÁRIO",
                "Funcionário alterado: ID = " + id
        );

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deletarFuncionario(
            @PathVariable Long id,
            Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        adminService.deletarFuncionario(id);

        logService.registrar(
                logado,
                "EXCLUIR FUNCIONÁRIO",
                "Funcionário removido: ID = " + id
        );

        return ResponseEntity.noContent().build();
    }

    // ------------------------ RELATÓRIOS ------------------------

    @GetMapping("/relatorio/loja")
    public ResponseEntity<RelatorioDTO> gerarRelatorioLoja(Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        logService.registrar(
                logado,
                "GERAR RELATÓRIO LOJA",
                "Relatório geral da loja acessado"
        );

        return ResponseEntity.ok(adminService.gerarRelatorioLoja());
    }

    @GetMapping("/relatorio/funcionarios")
    public ResponseEntity<RelatorioDTO> gerarRelatorioFuncionarios(Authentication auth) {

        Funcionario logado = getFuncionarioLogado(auth);

        logService.registrar(
                logado,
                "GERAR RELATÓRIO FUNCIONÁRIOS",
                "Relatório de funcionários acessado"
        );

        return ResponseEntity.ok(adminService.gerarRelatorioFuncionarios());
    }
}
