package com.stileunico.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stileunico.model.Funcionario;
import com.stileunico.model.LogAcao;
import com.stileunico.repository.FuncionarioRepository;
import com.stileunico.repository.LogAcaoRepository;

@Service
public class FuncionarioService {

    private static final Logger logger = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;
    private final LogAcaoRepository logAcaoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, LogAcaoRepository logAcaoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.logAcaoRepository = logAcaoRepository;
    }

    // ----------- CRIAR FUNCIONÁRIO -----------
    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        Funcionario salvo = funcionarioRepository.save(funcionario);
        registrarLog("Funcionário cadastrado: " + salvo.getNome() + " (Email: " + salvo.getEmail() + ")");
        logger.info("Funcionário cadastrado com sucesso: {}", salvo.getNome());
        return salvo;
    }

    // ----------- LISTAR TODOS -----------
    public List<Funcionario> listarFuncionarios() {
        logger.info("Listando todos os funcionários...");
        return funcionarioRepository.findAll();
    }

    // ----------- BUSCAR POR ID -----------
    public Funcionario buscarPorId(Long id) {
        logger.info("Buscando funcionário com ID {}", id);
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    // ----------- ATUALIZAR FUNCIONÁRIO -----------
    public Funcionario atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionario = buscarPorId(id);

        funcionario.setNome(funcionarioAtualizado.getNome());
        funcionario.setEmail(funcionarioAtualizado.getEmail());
        funcionario.setCargo(funcionarioAtualizado.getCargo());
        funcionario.setSenha(funcionarioAtualizado.getSenha());

        Funcionario atualizado = funcionarioRepository.save(funcionario);
        registrarLog("Funcionário atualizado: " + atualizado.getNome() + " (ID: " + id + ")");
        logger.info("Funcionário atualizado com sucesso: {}", atualizado.getNome());
        return atualizado;
    }

    // ----------- DELETAR FUNCIONÁRIO -----------
    public void deletarFuncionario(Long id) {
        funcionarioRepository.findById(id).ifPresentOrElse(f -> {
            funcionarioRepository.deleteById(id);
            registrarLog("Funcionário deletado: " + f.getNome() + " (ID: " + id + ")");
            logger.warn("Funcionário removido: {}", f.getNome());
        }, () -> {
            throw new RuntimeException("Funcionário não encontrado para exclusão");
        });
    }

    // ----------- MÉTODO AUXILIAR DE LOG -----------
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
