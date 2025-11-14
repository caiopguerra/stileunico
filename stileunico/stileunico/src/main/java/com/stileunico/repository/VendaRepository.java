package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stileunico.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
