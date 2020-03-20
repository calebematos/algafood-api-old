package com.calebematos.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.PedidoInputDisassembler;
import com.calebematos.algafood.api.assembler.PedidoModelAssembler;
import com.calebematos.algafood.api.assembler.PedidoResumoModelAssembler;
import com.calebematos.algafood.api.model.PedidoModel;
import com.calebematos.algafood.api.model.PedidoResumoModel;
import com.calebematos.algafood.api.model.input.PedidoInput;
import com.calebematos.algafood.core.data.PageableTranslator;
import com.calebematos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.model.Usuario;
import com.calebematos.algafood.domain.repository.PedidoRepository;
import com.calebematos.algafood.domain.repository.filter.PedidoFilter;
import com.calebematos.algafood.domain.service.PedidoService;
import com.calebematos.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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

	@GetMapping
	public Page<PedidoResumoModel> pesquisar(@PageableDefault(size = 10) Pageable pageable, PedidoFilter filtro) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		
		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos.getContent());
		
		Page<PedidoResumoModel> pedidosPage = new PageImpl<>(pedidosModel, pageable, pedidos.getTotalElements());
		return pedidosPage;
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			// TODO:pegar usuario logado 
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
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
