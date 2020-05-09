package com.calebematos.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.calebematos.algafood.domain.model.FotoProduto;
import com.calebematos.algafood.domain.repository.ProdutoRepository;
import com.calebematos.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getProduto().getId());
		String nomeFotoExistente = null;
		
		if(fotoExistente.isPresent()) {
			nomeFotoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}

		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		
		foto.setNomeArquivo(novoNomeArquivo);
		foto = produtoRepository.save(foto);

		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(inputStream)
				.build();

		fotoStorageService.substituir(nomeFotoExistente, novaFoto);
		
		return foto;
	}

	public FotoProduto buscar(Long restauranteId, Long produtoId) {

		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradoException(produtoId));
	}

	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = buscar(restauranteId, produtoId);
		
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		
		fotoStorageService.remover(fotoProduto.getNomeArquivo());
	}

}
