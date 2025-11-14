package com.stileunico.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stileunico.model.Funcionario;
import com.stileunico.model.LogAcao;
import com.stileunico.repository.LogAcaoRepository;

@Service
public class LogAcaoService {
    private final LogAcaoRepository repo;

    public LogAcaoService(LogAcaoRepository repo) {
        this.repo = repo;
    }

    public void registrar(Funcionario funcionario, String acao, String descricao) {
        LogAcao log = new LogAcao();
        log.setFuncionario(funcionario);
        log.setAcao(acao);
        log.setDescricao(descricao);
        log.setData(LocalDateTime.now());
        repo.save(log);
    }

    public List<LogAcao> listar() {
        return repo.findAll();
    }
}
