package com.calebematos.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.CidadeInputDisassembler;
import com.calebematos.algafood.api.assembler.CidadeModelAssembler;
import com.calebematos.algafood.api.model.CidadeModel;
import com.calebematos.algafood.api.model.input.CidadeInput;
import com.calebematos.algafood.domain.exception.EstadoNaoEncontradoException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.repository.CidadeRepository;
import com.calebematos.algafood.domain.service.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation("Lista todas as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscar(cidadeId);
		return cidadeModelAssembler.toModel(cidade);
	}

	@ApiOperation("Cadastra uma cidade")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CidadeModel adicionar(@ApiParam(name = "corpe", value = "Retresentação de uma nova cidade") 
		@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			cidade = cidadeService.salvar(cidade);
			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long cidadeId,
			
			@ApiParam(name = "corpe", value = "Retresentação de uma cidade com os novos dados") 
			@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cidadeService.buscar(cidadeId);
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			Cidade cidadeSalva = cidadeService.salvar(cidadeAtual);
			return cidadeModelAssembler.toModel(cidadeSalva);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade por ID")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}

}
