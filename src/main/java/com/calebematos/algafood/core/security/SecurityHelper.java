package com.calebematos.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.domain.repository.PedidoRepository;
import com.calebematos.algafood.domain.repository.RestauranteRepository;

@Component
public class SecurityHelper {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		if(restauranteId == null) {
			return false;
		}
		return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean gerenciaPedido(String codigoPedido) {
		return pedidoRepository.existsResponsavelPedido(codigoPedido, getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		
		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS") || gerenciaPedido(codigoPedido));
	}

	private boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
					.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
}
