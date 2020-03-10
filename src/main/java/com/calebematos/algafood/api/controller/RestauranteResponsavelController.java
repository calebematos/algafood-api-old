package com.calebematos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.UsuarioModelAssembler;
import com.calebematos.algafood.api.model.UsuarioModel;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public List<UsuarioModel> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios());
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
