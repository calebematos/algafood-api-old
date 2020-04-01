package com.calebematos.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.calebematos.algafood.api.exceptionhandler.Problem;
import com.calebematos.algafood.api.model.CidadeModel;
import com.calebematos.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista todas as cidades")
	CollectionModel<CidadeModel> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({ 
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) 
	})
	CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	 CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Retresentação de uma nova cidade", required = true) CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) 
	})
	CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(name = "corpe", value = "Retresentação de uma cidade com os novos dados", required = true) CidadeInput cidadeInput);

	@ApiResponses({ 
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) 
	})
	@ApiOperation("Exclui uma cidade por ID")
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}