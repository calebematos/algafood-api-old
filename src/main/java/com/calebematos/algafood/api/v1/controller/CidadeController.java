package com.calebematos.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.ResourceUriHelper;
import com.calebematos.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.calebematos.algafood.api.v1.assembler.CidadeModelAssembler;
import com.calebematos.algafood.api.v1.model.input.CidadeInput;
import com.calebematos.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.calebematos.algafood.domain.exception.EstadoNaoEncontradoException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.repository.CidadeRepository;
import com.calebematos.algafood.domain.service.CidadeService;
import com.calebematos.algafood.v1.api.model.CidadeModel;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi, ControllerPadrao<CidadeModel>{

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscar(cidadeId);
		return cidadeModelAssembler.toModel(cidade);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			cidade = cidadeService.salvar(cidade);
			ResourceUriHelper.addUriInResponseHeader(cidade.getId());
			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
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

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}

}
