package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.EntidadeEmUsoException;
import com.calebematos.algafood.domain.exception.GrupoNaoEncontradoException;
import com.calebematos.algafood.domain.model.Grupo;
import com.calebematos.algafood.domain.model.Permissao;
import com.calebematos.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;

	public Grupo buscar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}

	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscar(grupoId);
		Permissao permissao = permissaoService.buscar(permissaoId);
		
		grupo.adicionarPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscar(grupoId);
		Permissao permissao = permissaoService.buscar(permissaoId);
		
		grupo.removerPermissao(permissao);
	}

}
