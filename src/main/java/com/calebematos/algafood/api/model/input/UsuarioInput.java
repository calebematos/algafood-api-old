package com.calebematos.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@ApiModelProperty(example = "João da Silva", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "joao.ger@algafood.com.br", required = true)
	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String senha;
}
