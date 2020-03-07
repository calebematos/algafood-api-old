package com.calebematos.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

	List<Produto> findByRestauranteId(Long restauranteId);
	
	List<Produto> findByRestaurante(Restaurante restauranteId);
	
	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
	
	
}
