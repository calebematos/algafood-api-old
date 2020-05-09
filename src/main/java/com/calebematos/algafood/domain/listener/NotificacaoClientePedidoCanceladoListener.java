package com.calebematos.algafood.domain.listener;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.calebematos.algafood.domain.event.PedidoCanceladoEvent;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.service.EnvioEmailService;
import com.calebematos.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioEmailService;

	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoCanceladoEvent event) {

		Pedido pedido = event.getPedido();

		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
				.corpo("pedido-cancelado.html")
				.destinatarios(Set.of(pedido.getCliente().getEmail()))
				.variaveis(Map.of("pedido", pedido))
				.build();

		envioEmailService.enviar(mensagem);
	}
}
