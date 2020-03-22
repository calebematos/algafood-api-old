package com.calebematos.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.calebematos.algafood.core.validation.FileContentType;
import com.calebematos.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
