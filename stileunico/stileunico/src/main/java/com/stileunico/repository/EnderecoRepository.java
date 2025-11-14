package com.stileunico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stileunico.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {}