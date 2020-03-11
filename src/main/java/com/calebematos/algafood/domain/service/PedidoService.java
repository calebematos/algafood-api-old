package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calebematos.algafood.domain.exception.PedidoNaoEncontradoException;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
