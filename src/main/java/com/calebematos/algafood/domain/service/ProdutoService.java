package com.calebematos.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private RestauranteService restauranteService;
	
	public Produto buscar(Long produtoId) {
		return produtoRepository.findById(produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}
	
	public List<Produto> listarProdutos(Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		return produtoRepository.findByRestaurante(restaurante);
	}

	public Produto buscarPeloRestaurante(Long restauranteId, Long produtoId) {
		restauranteService.buscar(restauranteId);
		buscar(produtoId);
		return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(
						String.format("Não existe um cadastro de produto com código %s para o restaurantes de código %s", produtoId, restauranteId)));
	}

	@Transactional
	public Produto salvar(Produto produto, Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		produto.setRestaurante(restaurante);
		
		return produtoRepository.save(produto);
	}
	
	
}
