package com.stileunico.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stileunico.DTO.RelatorioDTO;
import com.stileunico.model.Funcionario;
import com.stileunico.model.LogAcao;
import com.stileunico.repository.FuncionarioRepository;
import com.stileunico.repository.LogAcaoRepository;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final FuncionarioRepository funcionarioRepository;
    private final LogAcaoRepository logAcaoRepository;

    public AdminService(FuncionarioRepository funcionarioRepository, LogAcaoRepository logAcaoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.logAcaoRepository = logAcaoRepository;
    }

    // ----------- FUNCIONÁRIOS -----------

    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        Funcionario salvo = funcionarioRepository.save(funcionario);

        registrarLog("Cadastro de funcionário realizado: " + salvo.getNome());
        logger.info("Funcionário cadastrado: {}", salvo.getNome());

        return salvo;
    }

    public List<Funcionario> consultarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        registrarLog("Consulta geral de funcionários. Total retornado: " + funcionarios.size());
        logger.info("Consulta geral de funcionários ({} registros)", funcionarios.size());

        return funcionarios;
    }

    public Funcionario alterarFuncionario(Long id, Funcionario funcionarioAtualizado) {
        Funcionario atualizado = funcionarioRepository.findById(id).map(f -> {
            f.setNome(funcionarioAtualizado.getNome());
            f.setEmail(funcionarioAtualizado.getEmail());
            f.setCpf(funcionarioAtualizado.getCpf());
            f.setRg(funcionarioAtualizado.getRg());
            f.setCargo(funcionarioAtualizado.getCargo());
            f.setCargaHoraria(funcionarioAtualizado.getCargaHoraria());
            f.setSalario(funcionarioAtualizado.getSalario());
            f.setEndereco(funcionarioAtualizado.getEndereco());
            f.setTelefone(funcionarioAtualizado.getTelefone());
            return funcionarioRepository.save(f);
        }).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        registrarLog("Alteração de funcionário (ID " + id + ") realizada.");
        logger.info("Funcionário ID {} alterado com sucesso.", id);

        return atualizado;
    }

    public void deletarFuncionario(Long id) {
        funcionarioRepository.deleteById(id);

        registrarLog("Exclusão de funcionário (ID " + id + ") realizada.");
        logger.info("Funcionário ID {} deletado.", id);
    }

    // ----------- RELATÓRIOS -----------

    public RelatorioDTO gerarRelatorioLoja() {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setTitulo("Relatório Geral da Loja");

        registrarLog("Relatório geral da loja gerado.");
        logger.info("Relatório geral da loja gerado.");

        return relatorio;
    }

    public RelatorioDTO gerarRelatorioFuncionarios() {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setTitulo("Relatório de Funcionários");

        registrarLog("Relatório de funcionários gerado.");
        logger.info("Relatório de funcionários gerado.");

        return relatorio;
    }

    // ----------- MÉTODO AUXILIAR -----------

    private void registrarLog(String descricao) {
        try {
            LogAcao log = new LogAcao();
            log.setDescricao(descricao);
            log.setData(LocalDateTime.now());
            logAcaoRepository.save(log);
        } catch (Exception e) {
            logger.error("Erro ao registrar log no banco: {}", e.getMessage());
        }
    }
}
