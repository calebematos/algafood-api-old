package com.calebematos.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.calebematos.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

}
