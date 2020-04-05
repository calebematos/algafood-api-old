package com.calebematos.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.controller.FormaPagamentoController;
import com.calebematos.algafood.api.controller.PedidoController;
import com.calebematos.algafood.api.controller.RestauranteController;
import com.calebematos.algafood.api.controller.RestauranteProdutoController;
import com.calebematos.algafood.api.controller.UsuarioController;
import com.calebematos.algafood.api.model.PedidoModel;
import com.calebematos.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

	@Autowired
	private ModelMapper modelMapper;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido); 
		modelMapper.map(pedido, pedidoModel);
		
		TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM)
				);
		
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		
		pedidoModel.add(new Link(UriTemplate.of(pedidosUrl, pageVariables), "pedidos"));
		
		pedidoModel.getCliente().add(
				linkTo(methodOn(UsuarioController.class).buscar(pedidoModel.getCliente().getId())).withSelfRel());
		
		pedidoModel.getRestaurante().add(
				linkTo(methodOn(RestauranteController.class).buscar(pedidoModel.getRestaurante().getId())).withSelfRel());
		
		pedidoModel.getFormaPagamento().add(
				linkTo(methodOn(FormaPagamentoController.class).buscar(pedidoModel.getFormaPagamento().getId(), null)).withSelfRel());
		
		pedidoModel.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class).buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId())).withSelfRel());
		});
		
		
		return pedidoModel;
	}
	
}
