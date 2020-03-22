package com.calebematos.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.calebematos.algafood.api.model.input.FotoProdutoInput;
import com.calebematos.algafood.domain.model.FotoProduto;
import com.calebematos.algafood.domain.model.Produto;
import com.calebematos.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Transactional
	private FotoProduto salvar(FotoProduto foto) {
		
		return produtoRepository.save(foto);
	}

	public FotoProduto converterESalvar(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput) {
		
		Produto produto = produtoService.buscarPeloRestaurante(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		
		foto.setProduto(produto);
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		return salvar(foto);
	}
}
