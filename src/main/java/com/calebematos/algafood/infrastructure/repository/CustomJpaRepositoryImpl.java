package com.calebematos.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.calebematos.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		
		this.manager = em;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName() ;
		
		T entity = manager.createQuery(jpql, getDomainClass())
					.setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);
	}

}
