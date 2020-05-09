package com.calebematos.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	FotoRecuperada recuperar(String nomeArquivo);
	
	void armazenar(NovaFoto novaFoto);

	void remover(String nomeArquivo);

	default void substituir(String nomeFotoExistente, NovaFoto novaFoto) {
		armazenar(novaFoto);
		if (nomeFotoExistente != null) {
			remover(nomeFotoExistente);
		}
	}

	default String gerarNomeArquivo(String nomeFoto) {
		return UUID.randomUUID().toString() + "+" + nomeFoto;
	}

	@Getter
	@Builder
	class NovaFoto {
		private InputStream inputStream;
		private String contentType;
		private String nomeArquivo;
	}
	
	@Getter
	@Builder
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}

		public boolean temInputStream() {
			return inputStream != null;
		}
	}

}
