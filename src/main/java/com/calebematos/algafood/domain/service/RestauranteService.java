package com.calebematos.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.model.FormaPagamento;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = cozinhaService.buscar(cozinhaId);
		restaurante.setCozinha(cozinha);

		Cidade cidade = cidadeService.buscar(cidadeId);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Long restauranteId, Restaurante restauranteAlterado) {
		Restaurante restauranteAtual = buscar(restauranteId);
		BeanUtils.copyProperties(restauranteAlterado, restauranteAtual, "id", "formasPagamento", "endereco",
				"dataCadastro");

		return salvar(restauranteAlterado);
	}

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public void ativarIntativar(Long restauranteId, Boolean ativo) {
		Restaurante restauranteAtual = buscar(restauranteId);
		if (ativo) {
			restauranteAtual.ativar();
		} else {
			restauranteAtual.inativar();
		}
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);
	}

}
