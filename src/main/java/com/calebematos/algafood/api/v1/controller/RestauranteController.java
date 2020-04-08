package com.calebematos.algafood.api.v1.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.calebematos.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.calebematos.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.calebematos.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.calebematos.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.calebematos.algafood.api.v1.model.RestauranteBasicoModel;
import com.calebematos.algafood.api.v1.model.RestauranteModel;
import com.calebematos.algafood.api.v1.model.input.RestauranteInput;
import com.calebematos.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.calebematos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.calebematos.algafood.domain.exception.ValidacaoException;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.repository.RestauranteRepository;
import com.calebematos.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path="/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
	}

	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		
		RestauranteModel restauranteModel = restauranteModelAssembler.toModel(restaurante);
		
		return restauranteModel;
		
	}

	@PostMapping
	public ResponseEntity<RestauranteModel> adicionar(@RequestBody @Valid RestauranteInput restaurante) {
		try {
			Restaurante restauranteSalvo = restauranteService.salvar(restauranteInputDisassembler.toDomainObject(restaurante));
			RestauranteModel restauranteModel = restauranteModelAssembler.toModel(restauranteSalvo);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteModel);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = restauranteService.buscar(restauranteId);
		
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		try {
			restauranteAtual = restauranteService.salvar(restauranteAtual);
			return ResponseEntity.ok(restauranteModelAssembler.toModel(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarInativar(@PathVariable Long restauranteId, @RequestBody Boolean ativo) {
		restauranteService.ativarIntativar(restauranteId, ativo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			restauranteService.ativar(restauranteIds);
			
			return ResponseEntity.noContent().build();
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			restauranteService.inativar(restauranteIds);
			return ResponseEntity.noContent().build();
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}
	

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {
		Optional<Restaurante> restauranteAtualOpt = restauranteRepository.findById(restauranteId);

		if (restauranteAtualOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Restaurante restauranteAtual = restauranteAtualOpt.get();
		merge(campos, restauranteAtual, request);
		
		validar(restauranteAtual, "restaurante");

//		return atualizar(restauranteId, restauranteAtual);
		return null;
	}

	private void validar(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
		
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}
