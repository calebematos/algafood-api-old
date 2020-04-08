package com.calebematos.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.calebematos.algafood.api.v1.model.input.ItemPedidoInput;
import com.calebematos.algafood.domain.model.Endereco;
import com.calebematos.algafood.domain.model.ItemPedido;
import com.calebematos.algafood.v1.api.model.EnderecoModel;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoMap.<String>addMapping(endereceSrc -> endereceSrc.getCidade().getNome(), 
				(enderecoDest, value) -> enderecoDest.setCidade(value));
		
		enderecoMap.<String>addMapping(endereceSrc -> endereceSrc.getCidade().getEstado().getNome(), 
				(enderecoDest, value) -> enderecoDest.setEstado(value));
		
		return modelMapper;
	}
}
