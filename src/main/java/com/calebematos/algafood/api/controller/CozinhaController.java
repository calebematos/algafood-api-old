package com.calebematos.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.calebematos.algafood.api.assembler.CozinhaInputDisassembler;
import com.calebematos.algafood.api.assembler.CozinhaModelAssembler;
import com.calebematos.algafood.api.model.CozinhaModel;
import com.calebematos.algafood.api.model.input.CozinhaInput;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.repository.CozinhaRepository;
import com.calebematos.algafood.domain.service.CozinhaService;

import io.swagger.annotations.Api;

@Api(tags = "Cozinhas")
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;  

	@GetMapping
	public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhas.getContent());
		
		Page<CozinhaModel> cozinhasPage = new PageImpl<>(cozinhasModel, pageable, cozinhas.getTotalElements());
		return cozinhasPage;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.buscar(cozinhaId);
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	public ResponseEntity<CozinhaModel> adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cozinhaService.salvar(cozinha);
		CozinhaModel cozinhaModel = cozinhaModelAssembler.toModel(cozinha);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaModel);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<CozinhaModel> atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		Cozinha cozinhaAtual = cozinhaService.buscar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
		
		CozinhaModel cozinhaModel = cozinhaModelAssembler.toModel(cozinhaAtual);
		return ResponseEntity.ok(cozinhaModel);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cozinhaService.excluir(cozinhaId);
	}
}
