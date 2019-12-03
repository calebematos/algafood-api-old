package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.calebematos.algafood.domain.exception.CidadeNaoEncontradoException;
import com.calebematos.algafood.domain.exception.EntidadeEmUsoException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.model.Estado;
import com.calebematos.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.buscar(estadoId);

		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradoException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradoException(cidadeId));
	}
}
