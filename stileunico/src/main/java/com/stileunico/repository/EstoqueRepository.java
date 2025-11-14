package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.stileunico.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByRoupaIdAndTamanho(Long roupaId, String tamanho);
}
