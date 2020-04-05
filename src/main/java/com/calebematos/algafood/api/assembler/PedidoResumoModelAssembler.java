package com.calebematos.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.controller.PedidoController;
import com.calebematos.algafood.api.controller.RestauranteController;
import com.calebematos.algafood.api.controller.UsuarioController;
import com.calebematos.algafood.api.model.PedidoResumoModel;
import com.calebematos.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
		modelMapper.map(pedido, pedidoResumoModel);

		pedidoResumoModel.add(linkTo(PedidoController.class).withRel("pedidos"));

		pedidoResumoModel.getCliente().add(
				linkTo(methodOn(UsuarioController.class).buscar(pedidoResumoModel.getCliente().getId())).withSelfRel());
		
		pedidoResumoModel.getRestaurante().add(
				linkTo(methodOn(RestauranteController.class).buscar(pedidoResumoModel.getRestaurante().getId())).withSelfRel());
		
		return pedidoResumoModel;
	}

}
