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
import com.calebematos.algafood.api.controller.GrupoController;
import com.calebematos.algafood.api.controller.GrupoPermissaoController;
import com.calebematos.algafood.api.controller.PedidoController;
import com.calebematos.algafood.api.controller.PermissaoController;
import com.calebematos.algafood.api.controller.RestauranteController;
import com.calebematos.algafood.api.controller.RestauranteFormaPagamentoController;
import com.calebematos.algafood.api.controller.RestauranteProdutoController;
import com.calebematos.algafood.api.controller.RestauranteProdutoFotoController;
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

		return new Link(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
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
		return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withRel(rel);
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

	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	private Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(rel);
	}

	public Link linkToFormasPagamento(String rel) {
		return linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormasPagamento() {
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
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

	public Link linkToProdutos(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, null)).withRel(rel);
	}

	public Link linkToProdutos(Long restauranteId) {
		return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativarInativar(restauranteId, false)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativarInativar(restauranteId, true)).withRel(rel);
	}

	public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPagamentoId))
				.withRel(rel);
	}

	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null)).withRel(rel);
	}

	public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel) {

		return linkTo(methodOn(RestauranteResponsavelController.class).desassociarUsuario(restauranteId, usuarioId))
				.withRel(rel);
	}

	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteResponsavelController.class).associarUsuario(restauranteId, null))
				.withRel(rel);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteResponsavelController.class).buscar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteProdutoFotoController.class).buscar(restauranteId, produtoId)).withRel(rel);
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
		return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withRel(rel);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupos(String rel) {
		return linkTo(GrupoController.class).withRel(rel);
	}

	public Link linkToGrupos() {
		return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).buscar(grupoId)).withRel(rel);
	}

	public Link linkToPermissoes(String rel) {
		return linkTo(PermissaoController.class).withRel(rel);
	}

	public Link linkToPermissoes() {
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId) {
		return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
		 return linkTo(methodOn(GrupoPermissaoController.class)
		            .associar(grupoId, null)).withRel(rel);
	}

	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
		 return linkTo(methodOn(GrupoPermissaoController.class)
		            .desassociar(grupoId, permissaoId)).withRel(rel);
	}
}
