package com.calebematos.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.calebematos.algafood.api.model.EnderecoModel;
import com.calebematos.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoMap.<String>addMapping(endereceSrc -> endereceSrc.getCidade().getNome(), 
				(enderecoDest, value) -> enderecoDest.setCidade(value));
		
		enderecoMap.<String>addMapping(endereceSrc -> endereceSrc.getCidade().getEstado().getNome(), 
				(enderecoDest, value) -> enderecoDest.setEstado(value));
		
		
		return modelMapper;
	}
}
