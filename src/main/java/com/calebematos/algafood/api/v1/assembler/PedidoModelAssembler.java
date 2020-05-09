package com.calebematos.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.v1.AlgaLinks;
import com.calebematos.algafood.api.v1.controller.PedidoController;
import com.calebematos.algafood.api.v1.controller.UsuarioController;
import com.calebematos.algafood.api.v1.model.PedidoModel;
import com.calebematos.algafood.core.security.SecurityHelper;
import com.calebematos.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks; 
	
	@Autowired
	private SecurityHelper securityHelper;
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido); 
		modelMapper.map(pedido, pedidoModel);
		
		pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		
		if(securityHelper.podeGerenciarPedidos(pedido.getCodigo())) {
			
			if(pedido.podeSerConfirmado()) {
				pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
			
			if(pedido.podeSerEntregue()) {
				pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
			
			if(pedido.podeSerCancelado()) {
				pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
		}
		
		pedidoModel.getCliente().add(algaLinks.linkToBuscar(UsuarioController.class, pedidoModel.getCliente().getId()));

		pedidoModel.getRestaurante().add(
				algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
		
		pedidoModel.getItens().forEach(item -> {
			item.add(algaLinks.linkToItemPedido(pedidoModel.getRestaurante().getId(), item.getProdutoId()));
		});
		
		
		return pedidoModel;
	}
	
}
