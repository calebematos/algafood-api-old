package com.calebematos.algafood.domain.event;

import com.calebematos.algafood.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

	private Pedido pedido;
}
