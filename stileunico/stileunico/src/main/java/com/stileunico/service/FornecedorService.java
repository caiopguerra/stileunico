package com.stileunico.service;

import com.stileunico.model.Fornecedor;
import com.stileunico.model.LogAcao;
import com.stileunico.repository.FornecedorRepository;
import com.stileunico.repository.LogAcaoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger logger = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;
    private final LogAcaoRepository logAcaoRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository, LogAcaoRepository logAcaoRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.logAcaoRepository = logAcaoRepository;
    }

    // ----------- CRIAR FORNECEDOR -----------
    public Fornecedor salvar(Fornecedor fornecedor) {
        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        registrarLog("Fornecedor criado: " + salvo.getNome() + " (CNPJ: " + salvo.getCnpj() + ")");
        logger.info("Fornecedor criado com sucesso: {}", salvo.getNome());
        return salvo;
    }

    // ----------- LISTAR TODOS -----------
    public List<Fornecedor> listarTodos() {
        logger.info("Listando todos os fornecedores...");
        return fornecedorRepository.findAll();
    }

    // ----------- BUSCAR POR ID -----------
    public Optional<Fornecedor> buscarPorId(Long id) {
        logger.info("Buscando fornecedor ID {}", id);
        return fornecedorRepository.findById(id);
    }

    // ----------- ATUALIZAR FORNECEDOR -----------
    public Optional<Fornecedor> atualizar(Long id, Fornecedor dadosAtualizados) {
        return fornecedorRepository.findById(id).map(fornecedor -> {
            fornecedor.setNome(dadosAtualizados.getNome());
            fornecedor.setCnpj(dadosAtualizados.getCnpj());
            fornecedor.setTelefone(dadosAtualizados.getTelefone());
            fornecedor.setEmail(dadosAtualizados.getEmail());
            fornecedor.setInscricaoEstadual(dadosAtualizados.getInscricaoEstadual());
            fornecedor.setRecebimentos(dadosAtualizados.getRecebimentos());
            Fornecedor atualizado = fornecedorRepository.save(fornecedor);

            registrarLog("Fornecedor atualizado: " + atualizado.getNome() + " (ID: " + id + ")");
            logger.info("Fornecedor atualizado com sucesso: {}", atualizado.getNome());
            return atualizado;
        });
    }

    // ----------- DELETAR FORNECEDOR -----------
    public boolean deletar(Long id) {
        return fornecedorRepository.findById(id).map(f -> {
            fornecedorRepository.delete(f);
            registrarLog("Fornecedor deletado: " + f.getNome() + " (ID: " + id + ")");
            logger.warn("Fornecedor removido: {}", f.getNome());
            return true;
        }).orElse(false);
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
