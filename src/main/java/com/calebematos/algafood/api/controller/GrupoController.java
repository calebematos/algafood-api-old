package com.calebematos.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.calebematos.algafood.api.assembler.GrupoInputDisassembler;
import com.calebematos.algafood.api.assembler.GrupoModelAssembler;
import com.calebematos.algafood.api.model.GrupoModel;
import com.calebematos.algafood.api.model.input.GrupoInput;
import com.calebematos.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.calebematos.algafood.domain.model.Grupo;
import com.calebematos.algafood.domain.repository.GrupoRepository;
import com.calebematos.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@GetMapping
	public CollectionModel<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}

	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		return grupoModelAssembler.toModel(grupoService.buscar(grupoId));
	}

	@PostMapping
	public ResponseEntity<GrupoModel> adicionar(@Valid @RequestBody GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		grupo = grupoService.salvar(grupo);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoModelAssembler.toModel(grupo));
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<GrupoModel> atualizar(@PathVariable Long grupoId, @Valid @RequestBody GrupoInput grupoInput){
		Grupo grupoAlterado = grupoInputDisassembler.toDomainObject(grupoInput);
		Grupo grupoAtual = grupoService.buscar(grupoId);
		BeanUtils.copyProperties(grupoAlterado, grupoAtual, "id");
		
		grupoAtual = grupoService.salvar(grupoAtual);
		
		return ResponseEntity.ok(grupoModelAssembler.toModel(grupoAtual));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{grupoId}")
	public void remover(@PathVariable Long grupoId) {
		grupoService.excluir(grupoId);
	}
	
}
