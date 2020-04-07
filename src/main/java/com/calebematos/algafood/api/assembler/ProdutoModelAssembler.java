package com.calebematos.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.AlgaLinks;
import com.calebematos.algafood.api.controller.RestauranteProdutoController;
import com.calebematos.algafood.api.model.ProdutoModel;
import com.calebematos.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModel.class);
	}

	public ProdutoModel toModel(Produto produto) {
		ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

		modelMapper.map(produto, produtoModel);

		produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
		
	    produtoModel.add(algaLinks.linkToFotoProduto(
	            produto.getRestaurante().getId(), produto.getId(), "foto"));

		return produtoModel;
	}

}
