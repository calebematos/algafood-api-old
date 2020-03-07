package com.calebematos.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.ProdutoInputDisassembler;
import com.calebematos.algafood.api.assembler.ProdutoModelAssembler;
import com.calebematos.algafood.api.model.ProdutoModel;
import com.calebematos.algafood.api.model.input.ProdutoInput;
import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
		return produtoModelAssembler.toCollectionModel(produtoService.listarProdutos(restauranteId));
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return produtoModelAssembler.toModel(produtoService.buscarPeloRestaurante(restauranteId, produtoId));
	}

	@PostMapping
	public ResponseEntity<ProdutoModel> adicionar(@PathVariable Long restauranteId,
			@Valid @RequestBody ProdutoInput produtoInput) {
		Produto produto = produtoInputDisassembler.toModel(produtoInput);
		produto = produtoService.salvar(produto, restauranteId);

		return ResponseEntity.status(HttpStatus.CREATED).body(produtoModelAssembler.toModel(produto));
	}

	@PutMapping("/{produtoId}")
	public ResponseEntity<ProdutoModel> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid @RequestBody ProdutoInput produtoInput) {
		Produto produto = produtoService.buscar(produtoId);
		produtoInputDisassembler.copyToDomainObject(produtoInput, produto);
		
		produto = produtoService.salvar(produto, restauranteId);
		
		return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
	}

}
