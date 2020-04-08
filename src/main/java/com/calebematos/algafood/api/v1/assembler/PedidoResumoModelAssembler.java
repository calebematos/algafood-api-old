package com.calebematos.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.v1.AlgaLinks;
import com.calebematos.algafood.api.v1.controller.PedidoController;
import com.calebematos.algafood.api.v1.controller.UsuarioController;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.v1.api.model.PedidoResumoModel;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
		modelMapper.map(pedido, pedidoResumoModel);

		pedidoResumoModel.add(algaLinks.linkToListar(PedidoController.class, "pedidos"));

		pedidoResumoModel.getCliente()
				.add(algaLinks.linkToBuscar(UsuarioController.class, pedidoResumoModel.getCliente().getId()));

		pedidoResumoModel.getRestaurante().add(
				algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		return pedidoResumoModel;
	}

}
