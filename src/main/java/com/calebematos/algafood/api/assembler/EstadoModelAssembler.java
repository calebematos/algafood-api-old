package com.calebematos.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.calebematos.algafood.api.controller.EstadoController;
import com.calebematos.algafood.api.model.EstadoModel;
import com.calebematos.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	public EstadoModel toModel(Estado estado) {
		
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
		modelMapper.map(estado, estadoModel);
		
		estadoModel.add(linkTo(methodOn(EstadoController.class).listar()).withRel("estados"));
		
		return estadoModel;
	}
	
	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(EstadoController.class).withSelfRel());
	}


}
