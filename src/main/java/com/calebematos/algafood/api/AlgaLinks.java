package com.calebematos.algafood.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.controller.ControllerPadrao;
import com.calebematos.algafood.api.controller.CozinhaController;
import com.calebematos.algafood.api.controller.FluxoPedidoController;
import com.calebematos.algafood.api.controller.FormaPagamentoController;
import com.calebematos.algafood.api.controller.PedidoController;
import com.calebematos.algafood.api.controller.RestauranteController;
import com.calebematos.algafood.api.controller.RestauranteProdutoController;
import com.calebematos.algafood.api.controller.RestauranteResponsavelController;
import com.calebematos.algafood.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {

	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	
	private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public Link linkToPedidos(String rel) {
	    TemplateVariables filtroVariables = new TemplateVariables(
	            new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
	    
	    String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(pedidosUrl, 
	            PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel(rel);
	}
	
	public Link linkToBuscar(Class<? extends ControllerPadrao<?>> controller, Long id) {
		return linkToBuscar(controller, id, IanaLinkRelations.SELF_VALUE);
	}

	public Link linkToBuscar(Class<? extends ControllerPadrao<?>> controller, Long id, String rel) {
		return linkTo(methodOn(controller).buscar(id)).withRel(rel);
	}

	public Link linkToListar(Class<?> controller, String rel) {
		return linkTo(controller).withRel(rel);
	}

	public Link linkToListar(Class<?> controller) {
		return linkToListar(controller, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class)
				.buscar(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestaurantes(String rel) {
	    String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
	}
	
	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId, null)).withRel(rel);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF_VALUE);
	}

	public Link linkToItemPedido(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withSelfRel();
	}

	public Link linkToItemPedido(Long restauranteId, Long produtoId) {
		return linkToItemPedido(restauranteId, produtoId, IanaLinkRelations.SELF_VALUE);
	}

	public Link linkToGruposUsuario(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).buscar(usuarioId)).withRel(rel);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
		return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteResponsavelController.class).buscar(restauranteId)).withRel(rel);
	}

	public Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinha(Long cozinhaId, String rel) {
		return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withRel(rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .fechar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .ativarInativar(restauranteId, false)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .ativarInativar(restauranteId, true)).withRel(rel);
	}
}
