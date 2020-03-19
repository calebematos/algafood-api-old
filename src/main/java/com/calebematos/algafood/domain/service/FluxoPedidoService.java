package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.buscar(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = pedidoService.buscar(pedidoId);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = pedidoService.buscar(pedidoId);
		pedido.cancelar();
	}
}
