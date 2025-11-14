package com.stileunico.mapper;

import java.util.List;

import com.stileunico.DTO.request.ClienteRequestDTO;
import com.stileunico.DTO.response.ClienteResponseDTO;
import com.stileunico.DTO.response.EnderecoResponseDTO;
import com.stileunico.model.Cliente;
import com.stileunico.model.Endereco;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNomeCompleto(dto.nomeCompleto());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());

        if (dto.enderecos() != null) {
            List<Endereco> enderecos = dto.enderecos().stream().map(e -> {
                Endereco endereco = new Endereco();
                endereco.setRua(e.rua());
                endereco.setNumero(e.numero());
                endereco.setBairro(e.bairro());
                endereco.setCidade(e.cidade());
                endereco.setCep(e.cep());
                endereco.setCliente(cliente); // 💥 IMPORTANTE 💥
                return endereco;
            }).toList();

            cliente.setEnderecos(enderecos);
        }

        return cliente;
    }

    public static ClienteResponseDTO toResponse(Cliente c) {
        return new ClienteResponseDTO(
            c.getId(),
            c.getNomeCompleto(),
            c.getCpf(),
            c.getEmail(),
            c.getTelefone(),
            c.getEnderecos().stream().map(e ->
                new EnderecoResponseDTO(
                    e.getId(),
                    e.getRua(),
                    e.getNumero(),
                    e.getBairro(),
                    e.getCidade(),
                    e.getCep()
                )
            ).toList()
        );
    }
}

