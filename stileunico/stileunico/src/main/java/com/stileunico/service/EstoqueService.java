package com.stileunico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stileunico.model.Estoque;
import com.stileunico.model.LogAcao;
import com.stileunico.model.Roupa;
import com.stileunico.repository.EstoqueRepository;
import com.stileunico.repository.LogAcaoRepository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class EstoqueService {

    private static final Logger logger = LoggerFactory.getLogger(EstoqueService.class);

    private final EstoqueRepository repo;
    private final LogAcaoRepository logAcaoRepository;

    public EstoqueService(EstoqueRepository repo, LogAcaoRepository logAcaoRepository) {
        this.repo = repo;
        this.logAcaoRepository = logAcaoRepository;
    }

    // ----------- ADICIONAR ESTOQUE -----------
    @Transactional
    public Estoque adicionar(Long roupaId, String tamanho, int qtd) {
        Estoque estoque = repo.findByRoupaIdAndTamanho(roupaId, tamanho)
                .orElseGet(() -> {
                    Estoque novo = new Estoque();
                    Roupa r = new Roupa();
                    r.setId(roupaId);
                    novo.setRoupa(r);
                    novo.setTamanho(tamanho);
                    novo.setQuantidade(0);
                    return novo;
                });

        estoque.setQuantidade(estoque.getQuantidade() + qtd);
        Estoque salvo = repo.save(estoque);

        registrarLog("Adicionado estoque: Roupa ID " + roupaId + 
                     ", tamanho " + tamanho + 
                     ", quantidade adicionada: " + qtd + 
                     ", novo total: " + salvo.getQuantidade());
        logger.info("Estoque atualizado (adicionado): Roupa ID {}, Tam {}, +{}", roupaId, tamanho, qtd);

        return salvo;
    }

    // ----------- REMOVER ESTOQUE -----------
    @Transactional
    public void remover(Long roupaId, String tamanho, int qtd) {
        var estoque = repo.findByRoupaIdAndTamanho(roupaId, tamanho)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        if (estoque.getQuantidade() < qtd) {
            logger.warn("Tentativa de remover {} unidades de Roupa ID {} (Tam {}), mas só há {}", qtd, roupaId, tamanho, estoque.getQuantidade());
            throw new RuntimeException("Estoque insuficiente");
        }

        estoque.setQuantidade(estoque.getQuantidade() - qtd);
        repo.save(estoque);

        registrarLog("Removido estoque: Roupa ID " + roupaId + 
                     ", tamanho " + tamanho + 
                     ", quantidade removida: " + qtd + 
                     ", novo total: " + estoque.getQuantidade());
        logger.info("Estoque atualizado (removido): Roupa ID {}, Tam {}, -{}", roupaId, tamanho, qtd);
    }

    // ----------- MÉTODO AUXILIAR PARA LOGS -----------
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
