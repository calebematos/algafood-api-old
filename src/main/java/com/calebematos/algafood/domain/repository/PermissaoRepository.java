package com.calebematos.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.calebematos.algafood.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long>{

}
