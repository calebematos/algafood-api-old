package com.calebematos.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.calebematos.algafood.api.assembler.CidadeInputDisassembler;
import com.calebematos.algafood.api.assembler.CidadeModelAssembler;
import com.calebematos.algafood.api.model.CidadeModel;
import com.calebematos.algafood.api.model.input.CidadeInput;
import com.calebematos.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.calebematos.algafood.domain.exception.EstadoNaoEncontradoException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.repository.CidadeRepository;
import com.calebematos.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscar(cidadeId);
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
		
		cidadeModel.add(linkTo(methodOn(CidadeController.class).buscar(cidadeModel.getId())).withSelfRel());
		cidadeModel.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));
		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).buscar(cidadeModel.getEstado().getId())).withSelfRel());
		
		return cidadeModel;
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
