package com.calebematos.algafood.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.calebematos.algafood.api.v1.assembler.PedidoModelAssembler;
import com.calebematos.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.calebematos.algafood.api.v1.model.PedidoModel;
import com.calebematos.algafood.api.v1.model.PedidoResumoModel;
import com.calebematos.algafood.api.v1.model.input.PedidoInput;
import com.calebematos.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.calebematos.algafood.core.data.PageWrapper;
import com.calebematos.algafood.core.data.PageableTranslator;
import com.calebematos.algafood.core.security.CheckSecurity;
import com.calebematos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.filter.PedidoFilter;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.repository.PedidoRepository;
import com.calebematos.algafood.domain.service.PedidoService;
import com.calebematos.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path="/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar( PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}

	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {   

		
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			
			novoPedido = pedidoService.emitir(novoPedido);
			
			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e); 
		}
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = Map.of(
				"codigo","codigo",
				"subtotal","subtotal",
				"taxaFrete","taxaFrete",
				"valorTotal","valorTotal",
				"status","status",
				"dataCriacao","dataCriacao",
				"restaurante.nome","restaurante.nome",
				"cliente.nome","cliente.nome"
				);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}

	

}
