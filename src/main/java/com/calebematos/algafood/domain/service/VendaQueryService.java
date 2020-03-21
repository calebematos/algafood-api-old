package com.calebematos.algafood.domain.service;

import java.util.List;

import com.calebematos.algafood.domain.filter.VendaDiariaFilter;
import com.calebematos.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
