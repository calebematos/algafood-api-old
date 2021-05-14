package com.calebematos.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.calebematos.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.calebematos.algafood.api.v1.model.CozinhaModel;
import com.calebematos.algafood.api.v1.model.input.CozinhaInput;
import com.calebematos.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.calebematos.algafood.core.security.CheckSecurity;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.repository.CozinhaRepository;
import com.calebematos.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping(path="/v1/cozinhas",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi{

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;  
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

//	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel;
	}

	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscar(cozinhaId);
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@PostMapping
	public ResponseEntity<CozinhaModel> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);
		CozinhaModel cozinhaModel = cozinhaModelAssembler.toModel(cozinha);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaModel);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		Cozinha cozinhaAtual = cozinhaService.buscar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		CozinhaModel cozinhaModel = cozinhaModelAssembler.toModel(cozinhaAtual);
		return ResponseEntity.ok(cozinhaModel);
	}

	@CheckSecurity.Cozinhas.PodeEditar
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
}
