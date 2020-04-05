package com.calebematos.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.UsuarioModelAssembler;
import com.calebematos.algafood.api.model.UsuarioModel;
import com.calebematos.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path="/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public CollectionModel<UsuarioModel> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
				.removeLinks()
				.add(linkTo(methodOn(RestauranteResponsavelController.class).buscar(restauranteId)).withSelfRel());
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarUsuario(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarUsuario(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarUsuario(restauranteId, usuarioId);
	}

}
