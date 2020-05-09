package com.calebematos.algafood.domain.exception;

public class CidadeNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradoException(Long cidadeId) {
		this(String.format("Não existe cidade de cozinha com código %d", cidadeId));
	}

}
