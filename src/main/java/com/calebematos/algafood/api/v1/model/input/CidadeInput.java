package com.calebematos.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@ApiModelProperty(example = "Blumenau")
	private String nome;
	
	private EstadoIdInput estado;

}
