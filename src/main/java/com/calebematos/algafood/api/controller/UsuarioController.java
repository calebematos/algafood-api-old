package com.calebematos.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.UsuarioInputDisassembler;
import com.calebematos.algafood.api.assembler.UsuarioModelAssembler;
import com.calebematos.algafood.api.model.UsuarioModel;
import com.calebematos.algafood.api.model.input.SenhaInput;
import com.calebematos.algafood.api.model.input.UsuarioInput;
import com.calebematos.algafood.api.model.input.UsuarioSemSenhaInput;
import com.calebematos.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.calebematos.algafood.domain.model.Usuario;
import com.calebematos.algafood.domain.repository.UsuarioRepository;
import com.calebematos.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping(path="/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioModel> listar(){
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		return usuarioModelAssembler.toModel(usuarioService.buscar(usuarioId));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioModel> adicionar(@Valid @RequestBody UsuarioInput usuarioInput){
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = usuarioService.salvar(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelAssembler.toModel(usuario));
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<UsuarioModel> atualizar(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioSemSenhaInput usuarioInput ){
		Usuario usuarioAtual =  usuarioService.buscar(usuarioId);

		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		
		return ResponseEntity.ok(usuarioModelAssembler.toModel(usuarioAtual));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{usuarioId}/senha")
	public void alterarSenha(@PathVariable Long usuarioId, @Valid @RequestBody SenhaInput senhaInput) {
		usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

}
