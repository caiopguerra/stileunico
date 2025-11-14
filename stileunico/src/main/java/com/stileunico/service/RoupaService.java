package com.stileunico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stileunico.model.Funcionario;
import com.stileunico.model.Roupa;
import com.stileunico.repository.RoupaRepository;

@Service
public class RoupaService {

    private final RoupaRepository roupaRepository;
    private final LogAcaoService logService;

    public RoupaService(RoupaRepository roupaRepository, LogAcaoService logService) {
        this.roupaRepository = roupaRepository;
        this.logService = logService;
    }

    // Cadastrar nova roupa
    public Roupa cadastrar(Roupa roupa, Funcionario funcionario) {
        Roupa salva = roupaRepository.save(roupa);
        logService.registrar(funcionario, "Cadastro", "Roupa cadastrada: " + roupa.getNome());
        return salva;
    }

    // Listar todas as roupas
    public List<Roupa> listar(Funcionario funcionario) {
        List<Roupa> roupas = roupaRepository.findAll();
        logService.registrar(funcionario, "Consulta", "Listagem de todas as roupas.");
        return roupas;
    }

    // Buscar roupa por ID
    public Roupa buscarPorId(Long id, Funcionario funcionario) {
        Roupa roupa = roupaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Roupa não encontrada"));
        logService.registrar(funcionario, "Consulta", "Busca de roupa por ID: " + id);
        return roupa;
    }

    // Atualizar roupa
    public Roupa atualizar(Long id, Roupa atualizada, Funcionario funcionario) {
        Roupa r = roupaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Roupa não encontrada"));

        r.setNome(atualizada.getNome());
        r.setCor(atualizada.getCor());
        r.setTipo(atualizada.getTipo());
        r.setTamanho(atualizada.getTamanho());
        r.setPreco(atualizada.getPreco());
        r.setDescricao(atualizada.getDescricao());
        r.setMarca(atualizada.getMarca());
        r.setEstoque(atualizada.getEstoque());
        r.setOferta(atualizada.getOferta());
        r.setFotos(atualizada.getFotos());

        Roupa atualizadaFinal = roupaRepository.save(r);
        logService.registrar(funcionario, "Atualização", "Roupa atualizada: " + r.getNome() + " (ID: " + id + ")");
        return atualizadaFinal;
    }

    // Deletar roupa
    public void deletar(Long id, Funcionario funcionario) {
        roupaRepository.deleteById(id);
        logService.registrar(funcionario, "Exclusão", "Roupa deletada com ID: " + id);
    }
}
