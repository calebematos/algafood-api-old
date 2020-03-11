package com.calebematos.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.calebematos.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoModel {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private StatusPedido status;
	private OffsetDateTime dataCriacao;
	private UsuarioModel cliente;
	private RestauranteResumoModel restaurante;
}
