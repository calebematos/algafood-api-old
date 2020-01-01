package com.calebematos.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.model.CozinhaModel;
import com.calebematos.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaModel toModel(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaModel.class);
	}

	public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
