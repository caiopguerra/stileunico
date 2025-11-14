package com.stileunico.controller;

import com.stileunico.DTO.request.RoupaRequestDTO;
import com.stileunico.DTO.response.RoupaResponseDTO;
import com.stileunico.mapper.RoupaMapper;
import com.stileunico.model.Funcionario;
import com.stileunico.service.RoupaService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roupas")
public class RoupaController {

    private final RoupaService roupaService;

    public RoupaController(RoupaService roupaService) {
        this.roupaService = roupaService;
    }

    // --------------------- MÉTODO AUXILIAR ----------------------------

    private Funcionario getFuncionario(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof Funcionario)) {
            throw new RuntimeException("Usuário não autenticado.");
        }
        return (Funcionario) auth.getPrincipal();
    }

    // --------------------------- CREATE -------------------------------

    @PostMapping
    public RoupaResponseDTO cadastrar(
            @RequestBody RoupaRequestDTO dto,
            Authentication auth
    ) {
        Funcionario funcionario = getFuncionario(auth);

        return RoupaMapper.toResponse(
                roupaService.cadastrar(RoupaMapper.toEntity(dto), funcionario)
        );
    }

    // --------------------------- READ ALL -----------------------------

    @GetMapping
    public List<RoupaResponseDTO> listar(Authentication auth) {
        Funcionario funcionario = getFuncionario(auth);

        return roupaService.listar(funcionario)
                .stream()
                .map(RoupaMapper::toResponse)
                .toList();
    }

    // --------------------------- READ BY ID ---------------------------

    @GetMapping("/{id}")
    public RoupaResponseDTO buscarPorId(
            @PathVariable Long id,
            Authentication auth
    ) {
        Funcionario funcionario = getFuncionario(auth);

        return RoupaMapper.toResponse(
                roupaService.buscarPorId(id, funcionario)
        );
    }

    // --------------------------- UPDATE -------------------------------

    @PutMapping("/{id}")
    public RoupaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody RoupaRequestDTO dto,
            Authentication auth
    ) {
        Funcionario funcionario = getFuncionario(auth);

        return RoupaMapper.toResponse(
                roupaService.atualizar(id, RoupaMapper.toEntity(dto), funcionario)
        );
    }

    // --------------------------- DELETE -------------------------------

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable Long id,
            Authentication auth
    ) {
        Funcionario funcionario = getFuncionario(auth);
        roupaService.deletar(id, funcionario);
    }
}
