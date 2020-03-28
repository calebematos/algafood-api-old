package com.calebematos.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.calebematos.algafood.api.assembler.EstadoInputDisassembler;
import com.calebematos.algafood.api.assembler.EstadoModelAssembler;
import com.calebematos.algafood.api.model.EstadoModel;
import com.calebematos.algafood.api.model.input.EstadoInput;
import com.calebematos.algafood.domain.model.Estado;
import com.calebematos.algafood.domain.repository.EstadoRepository;
import com.calebematos.algafood.domain.service.EstadoService;

import io.swagger.annotations.Api;

@Api(tags = "Estados")
@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscar(estadoId);
		return estadoModelAssembler.toModel(estado);
	}

	@PostMapping
	public ResponseEntity<EstadoModel> adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		estado = estadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoModelAssembler.toModel(estado));
	}

	@PutMapping("/{estadoId}")
	public ResponseEntity<EstadoModel> atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		Estado estadoAtual = estadoService.buscar(estadoId);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		estadoAtual = estadoService.salvar(estadoAtual);
		return ResponseEntity.ok(estadoModelAssembler.toModel(estadoAtual));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}

}
