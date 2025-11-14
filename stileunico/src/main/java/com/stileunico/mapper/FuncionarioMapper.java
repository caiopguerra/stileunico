package com.stileunico.mapper;

import com.stileunico.DTO.request.FuncionarioRequestDTO;
import com.stileunico.DTO.response.FuncionarioResponseDTO;
import com.stileunico.model.Funcionario;

public class FuncionarioMapper {

    public static Funcionario toEntity(FuncionarioRequestDTO dto) {
        Funcionario f = new Funcionario();
        f.setNome(dto.nome());
        f.setEmail(dto.email());
        f.setSenha(dto.senha());
        f.setCpf(dto.cpf());
        f.setRg(dto.rg());
        f.setCargaHoraria(dto.cargoHoraria());
        // Garante que o salário nunca seja nulo
       f.setSalario(dto.salario() == 0 ? 0.0 : dto.salario());
        f.setCargo(dto.cargo());
        return f;
    }

    public static FuncionarioResponseDTO toResponse(Funcionario entity) {
        return new FuncionarioResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getRg(),
                entity.getCargaHoraria(),
                // Aqui também previne NPE se o banco tiver valor nulo
                entity.getSalario() == null ? 0.0 : entity.getSalario(),
                entity.getCargo());
    }
}
