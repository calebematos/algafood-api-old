package com.calebematos.algafood.v1.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
	
	@ApiModelProperty(example = "38400-000")
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto")
	private String logradouro;

	@ApiModelProperty(example = "\"1500\"")
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento;

	@ApiModelProperty(example = "Centro")
	private String bairro;
	
	@ApiModelProperty(example = "Uberlândia")
	private String cidade;
	
	@ApiModelProperty(example = "Minas Gerais")
	private String estado;
}
