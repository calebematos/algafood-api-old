package com.calebematos.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private EnvioEmailService envioEmailService;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		pedido.confirmar();

		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante()
				.getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html")
				.destinatarios(Set.of(pedido.getCliente().getEmail()))
				.variaveis(Map.of("pedido", pedido))
				.build();

		envioEmailService.enviar(mensagem);
	}

	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		pedido.entregar();
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		pedido.cancelar();
	}
}
