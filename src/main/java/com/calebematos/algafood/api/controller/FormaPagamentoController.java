package com.calebematos.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.assembler.FormaPagamentoAssembler;
import com.calebematos.algafood.api.model.FormaPagamentoModel;
import com.calebematos.algafood.domain.repository.FormaPagamentoRepository;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoAssembler formaPagamentoAssembler;
	
	public List<FormaPagamentoModel> listar(){
		return formaPagamentoAssembler.toCollectionModel(formaPagamentoRepository.findAll());
	}
}
