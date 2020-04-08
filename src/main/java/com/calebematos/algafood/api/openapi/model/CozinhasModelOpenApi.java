package com.calebematos.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.calebematos.algafood.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi{

	
	private CozinhasEmbeddedModelOpenApi _embeded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModel> cozinhas;
	}
}
