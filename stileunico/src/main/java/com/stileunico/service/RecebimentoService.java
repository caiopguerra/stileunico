package com.stileunico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stileunico.model.Funcionario;
import com.stileunico.model.ItemRecebimento;
import com.stileunico.model.Recebimento;
import com.stileunico.repository.RecebimentoRepository;

import jakarta.transaction.Transactional;

@Service
public class RecebimentoService {
    private final RecebimentoRepository repo;
    private final EstoqueService estoqueService;
    private final LogAcaoService logService;

    public RecebimentoService(RecebimentoRepository repo, EstoqueService estoqueService, LogAcaoService logService) {
        this.repo = repo;
        this.estoqueService = estoqueService;
        this.logService = logService;
    }

    @Transactional
    public Recebimento registrar(Recebimento recebimento, Funcionario funcionario) {
        for (ItemRecebimento item : recebimento.getItens()) {
            estoqueService.adicionar(item.getRoupa().getId(), item.getTamanho(), item.getQuantidade());
        }

        Recebimento salvo = repo.save(recebimento);

        // 🔹 Cria um registro de log
        String descricao = "Recebimento registrado com " + recebimento.getItens().size() + " itens.";
        logService.registrar(funcionario, "Cadastro de Recebimento", descricao);

        return salvo;
    }

    public List<Recebimento> listar(Funcionario funcionario) {
        List<Recebimento> recebimentos = repo.findAll();

        // 🔹 Cria log de listagem (opcional, pode remover se quiser menos ruído)
        logService.registrar(funcionario, "Consulta", "Listagem de todos os recebimentos.");

        return recebimentos;
    }
}
