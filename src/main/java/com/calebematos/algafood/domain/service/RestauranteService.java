package com.calebematos.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cozinhaService.buscar(cozinhaId);
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Long restauranteId, Restaurante restauranteAlterado) {
		Restaurante restauranteAtual = buscar(restauranteId);
		BeanUtils.copyProperties(restauranteAlterado, restauranteAtual, "id", "formasPagamento", "endereco",
				"dataCadastro");

		return salvar(restauranteAlterado);
	}

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
