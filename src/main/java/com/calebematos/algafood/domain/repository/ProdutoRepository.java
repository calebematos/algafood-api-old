package com.calebematos.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

	List<Produto> findByRestauranteId(Long restauranteId);
	
	List<Produto> findTodosByRestaurante(Restaurante restauranteId);
	
	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
	
	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
}
