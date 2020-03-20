package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.exception.PedidoNaoEncontradoException;
import com.calebematos.algafood.domain.model.Cidade;
import com.calebematos.algafood.domain.model.FormaPagamento;
import com.calebematos.algafood.domain.model.Pedido;
import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.model.Restaurante;
import com.calebematos.algafood.domain.model.Usuario;
import com.calebematos.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Pedido buscar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	private void validarPedido(Pedido pedido) {
		Cidade cidade = cidadeService.buscar(pedido.getEndereco().getCidade().getId());
		Usuario cliente = usuarioService.buscar(pedido.getCliente().getId());
		Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
		
		pedido.getEndereco().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita pelo restaurante '%s'", formaPagamento.getDescricao(), restaurante.getNome()));
		}
	}

	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarPeloRestaurante(pedido.getRestaurante().getId(), item.getProduto().getId());
			item.setProduto(produto);
			item.setPedido(pedido);
			item.setPrecoUnitario(produto.getPreco());
		});
		
	}
	
	
	
}
