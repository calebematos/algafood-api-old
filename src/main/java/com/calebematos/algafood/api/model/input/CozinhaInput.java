package com.calebematos.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

public class CozinhaInput {

	@NotBlank
	private String nome;
}
