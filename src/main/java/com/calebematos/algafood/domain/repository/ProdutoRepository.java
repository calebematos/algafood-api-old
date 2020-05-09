package com.calebematos.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.calebematos.algafood.domain.model.FotoProduto;
import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery{

	List<Produto> findByRestauranteId(Long restauranteId);
	
	List<Produto> findTodosByRestaurante(Restaurante restauranteId);
	
	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
	
	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
	
	@Query("from FotoProduto f where f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long produtoId);

	@Query("select f from FotoProduto f join f.produto p where f.produto.id = :produtoId and p.restaurante.id = :restauranteId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
