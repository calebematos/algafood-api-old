package com.calebematos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.domain.filter.VendaDiariaFilter;
import com.calebematos.algafood.domain.model.dto.VendaDiaria;
import com.calebematos.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

	@Autowired
	private VendaQueryService vendaQueryService;

	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		return vendaQueryService.consultarVendasDiarias(filtro);
	}
}
