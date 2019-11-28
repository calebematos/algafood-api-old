package com.calebematos.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calebematos.algafood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
