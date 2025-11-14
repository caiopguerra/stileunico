package com.stileunico.controller;

import com.stileunico.model.Funcionario;
import com.stileunico.model.LogAcao;
import com.stileunico.service.LogAcaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogAcaoController {

    private final LogAcaoService logService;

    public LogAcaoController(LogAcaoService logService) {
        this.logService = logService;
    }

    // -------------------- PEGAR FUNCIONÁRIO LOGADO --------------------

    private Funcionario getFuncionarioLogado() {
        return (Funcionario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    // ------------------------ LISTAR LOGS -----------------------------

    @GetMapping
    public ResponseEntity<List<LogAcao>> listarLogs() {
        return ResponseEntity.ok(logService.listar());
    }

    // ----------- REGISTRO MANUAL (APENAS PARA TESTE OPCIONAL) -----------

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarAcaoManual(@RequestBody LogAcao req) {

        Funcionario func = getFuncionarioLogado();

        logService.registrar(
                func,
                req.getAcao(),
                req.getDescricao()
        );

        return ResponseEntity.ok("Log registrado com sucesso.");
    }
}
