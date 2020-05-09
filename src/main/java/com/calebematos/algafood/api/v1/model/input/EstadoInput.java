package com.calebematos.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class EstadoInput {
	
	@ApiModelProperty(example = "Minas Gerais", required = true)
	@NotBlank
	private String nome;
	
}
