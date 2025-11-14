package com.stileunico.controller;

import com.stileunico.DTO.response.AuthResponseDTO;
import com.stileunico.DTO.request.FuncionarioRequestDTO;
import com.stileunico.DTO.request.LoginRequestDTO;
import com.stileunico.model.Funcionario;
import com.stileunico.repository.FuncionarioRepository;
import com.stileunico.security.JwtService;
import com.stileunico.service.LogAcaoService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final FuncionarioRepository funcionarioRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final LogAcaoService logService;

    public AuthController(
            PasswordEncoder passwordEncoder,
            FuncionarioRepository funcionarioRepository,
            AuthenticationManager authManager,
            JwtService jwtService,
            LogAcaoService logService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.funcionarioRepository = funcionarioRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.logService = logService;
    }

    // ------------------------- REGISTRAR FUNCIONÁRIO -------------------------

    @PostMapping("/registrar")
    public Funcionario registrar(@RequestBody FuncionarioRequestDTO dto) {

        Funcionario f = new Funcionario();
        f.setNome(dto.nome());
        f.setEmail(dto.email());
        f.setSenha(passwordEncoder.encode(dto.senha()));
        f.setCargo(dto.cargo());

        Funcionario salvo = funcionarioRepository.save(f);

        // Log do cadastro
        logService.registrar(
                salvo,
                "CADASTRO",
                "Funcionário criado no sistema: " + salvo.getEmail()
        );

        return salvo;
    }

    // ------------------------- LOGIN -------------------------

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.senha()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Credenciais inválidas.");
        }

        // Recupera funcionário do banco
        Funcionario funcionario = funcionarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera token com cargo
        String token = jwtService.generateToken(funcionario.getEmail(), funcionario.getCargo());

        // Log de login
        logService.registrar(
                funcionario,
                "LOGIN",
                "Funcionário realizou login: " + funcionario.getEmail()
        );

        return new AuthResponseDTO(token);
    }
}
