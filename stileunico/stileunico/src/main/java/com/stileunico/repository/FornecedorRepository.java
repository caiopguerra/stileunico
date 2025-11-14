package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stileunico.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
