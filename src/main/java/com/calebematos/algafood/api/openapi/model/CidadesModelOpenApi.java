package com.calebematos.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.calebematos.algafood.api.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadeModel")
@Data
public class CidadesModelOpenApi {

	private CidadesEmbeddedModelOpenApi _embeded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadesEmbeddedModelOpenApi {
		
		private List<CidadeModel> cidades;
	}
}
