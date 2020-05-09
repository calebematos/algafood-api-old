package com.calebematos.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.v1.AlgaLinks;
import com.calebematos.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.calebematos.algafood.api.v1.model.PermissaoModel;
import com.calebematos.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.calebematos.algafood.domain.model.Grupo;
import com.calebematos.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(path="/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<PermissaoModel> buscar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscar(grupoId);
		
		 CollectionModel<PermissaoModel> permissoesModel 
	        = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
	            .removeLinks()
	            .add(algaLinks.linkToGrupoPermissoes(grupoId))
	            .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
	    
	    permissoesModel.getContent().forEach(permissaoModel -> {
	        permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
	                grupoId, permissaoModel.getId(), "desassociar"));
	    });
	    
	    return permissoesModel;
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}

	
}
