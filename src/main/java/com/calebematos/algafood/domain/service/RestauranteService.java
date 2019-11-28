package com.calebematos.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calebematos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.repository.CozinhaRepository;
import com.calebematos.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Optional<Cozinha> cozinhaOpt = cozinhaRepository.findById(cozinhaId);
		
		if(cozinhaOpt.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinhaOpt.get());
		
		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Restaurante restauranteAtual, Restaurante restauranteAlterado) {
		BeanUtils.copyProperties(restauranteAlterado, restauranteAtual, "id");
		
		return salvar(restauranteAlterado);
	}
	
}
