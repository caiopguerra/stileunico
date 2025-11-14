package com.stileunico.service;

import com.stileunico.model.Cliente;
import com.stileunico.model.Endereco;
import com.stileunico.model.LogAcao;
import com.stileunico.repository.ClienteRepository;
import com.stileunico.repository.LogAcaoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final LogAcaoRepository logAcaoRepository;

    public ClienteService(ClienteRepository clienteRepository, LogAcaoRepository logAcaoRepository) {
        this.clienteRepository = clienteRepository;
        this.logAcaoRepository = logAcaoRepository;
    }

    // ----------- CREATE -----------
    public Cliente save(Cliente cliente) {
        if (cliente.getEnderecos() != null) {
            for (Endereco e : cliente.getEnderecos()) {
                e.setCliente(cliente);
            }
        }

        Cliente salvo = clienteRepository.save(cliente);

        registrarLog("Cliente cadastrado: " + salvo.getNomeCompleto() + " (ID " + salvo.getId() + ")");
        logger.info("Cliente cadastrado com sucesso: {} (ID {})", salvo.getNomeCompleto(), salvo.getId());

        return salvo;
    }

    // ----------- READ by ID -----------
    public Cliente findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        registrarLog("Consulta de cliente por ID: " + id);
        logger.info("Consulta de cliente por ID {}", id);

        return cliente;
    }

    // ----------- READ ALL -----------
    public List<Cliente> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();

        registrarLog("Consulta geral de clientes. Total retornado: " + clientes.size());
        logger.info("Consulta geral de clientes ({} registros)", clientes.size());

        return clientes;
    }

    // ----------- UPDATE -----------
    public Cliente update(Long id, Cliente clienteAtualizado) {
        Cliente existente = findById(id);

        existente.setNomeCompleto(clienteAtualizado.getNomeCompleto());
        existente.setCpf(clienteAtualizado.getCpf());
        existente.setEmail(clienteAtualizado.getEmail());

        Cliente atualizado = clienteRepository.save(existente);

        registrarLog("Atualização de cliente: " + atualizado.getNomeCompleto() + " (ID " + id + ")");
        logger.info("Cliente atualizado: {} (ID {})", atualizado.getNomeCompleto(), id);

        return atualizado;
    }

    // ----------- DELETE -----------
    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }

        clienteRepository.deleteById(id);

        registrarLog("Exclusão de cliente (ID " + id + ")");
        logger.info("Cliente ID {} excluído com sucesso", id);
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
