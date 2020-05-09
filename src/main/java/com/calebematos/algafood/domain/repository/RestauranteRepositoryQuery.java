package com.calebematos.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.calebematos.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQuery {
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}
