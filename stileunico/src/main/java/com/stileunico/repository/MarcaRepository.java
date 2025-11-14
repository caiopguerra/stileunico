package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stileunico.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
