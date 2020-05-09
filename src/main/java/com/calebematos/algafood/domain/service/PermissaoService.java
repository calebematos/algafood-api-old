package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calebematos.algafood.domain.exception.PermissaoNaoEncontradoException;
import com.calebematos.algafood.domain.model.Permissao;
import com.calebematos.algafood.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao buscar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradoException(permissaoId));
	}
}
