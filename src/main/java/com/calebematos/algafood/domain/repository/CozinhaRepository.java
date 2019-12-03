package com.calebematos.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.calebematos.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

}
