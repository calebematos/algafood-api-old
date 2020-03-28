package com.calebematos.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.Query;

import com.calebematos.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long>{

	@Query("select max(dataAtualizacao) from FormaPagamento")
	OffsetDateTime getUltimaAtualizacao();

	@Query("select dataAtualizacao from FormaPagamento where id = :formaPagamentoId")
	OffsetDateTime getUltimaAtualizacaoById(Long formaPagamentoId);
}
