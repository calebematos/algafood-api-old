package com.calebematos.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.model.FotoProduto;
import com.calebematos.algafood.domain.repository.ProdutoRepositoryQuery;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}

	@Override
	public void delete(FotoProduto foto) {
		manager.remove(foto);
		
	}


}
