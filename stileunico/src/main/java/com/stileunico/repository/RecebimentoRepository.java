package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stileunico.model.Recebimento;

public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {
}
