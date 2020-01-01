package com.calebematos.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhdaIdInput {

	@NotNull
	private Long id;
}
