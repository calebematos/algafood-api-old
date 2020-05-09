package com.calebematos.algafood.domain.exception;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoNaoEncontradoException(Long formaPagamentoId) {
		this(String.format("Não existe cadastro de forma de pagamento com código %d", formaPagamentoId));
	}

}
