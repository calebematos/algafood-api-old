package com.calebematos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.PedidoModelAssembler;
import com.calebematos.algafood.api.model.PedidoModel;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.repository.PedidoRepository;
import com.calebematos.algafood.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@GetMapping
	public List<PedidoModel> listar() {
		return pedidoModelAssembler.toCollectionModel(pedidoRepository.findAll());
	}

	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscar(pedidoId);
		return pedidoModelAssembler.toModel(pedido);
	}

}
