package com.calebematos.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.FotoProdutoModelAssembler;
import com.calebematos.algafood.api.model.FotoProdutoModel;
import com.calebematos.algafood.api.model.input.FotoProdutoInput;
import com.calebematos.algafood.domain.model.FotoProduto;
import com.calebematos.algafood.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFoto {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) {
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.converterESalvar(restauranteId,produtoId, fotoProdutoInput);
		
		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
}
