package com.calebematos.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.api.model.input.SenhaInput;
import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.calebematos.algafood.domain.model.Usuario;
import com.calebematos.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario buscar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId) );
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public void alterarSenha(Long usuarioId, SenhaInput senhaInput) {
		Usuario usuario = buscar(usuarioId);
		
		if(!usuario.getSenha().equals(senhaInput.getSenhaAtual())) {
			throw new NegocioException("Senha atual informada não conincide com a senha do usuário");
		}
		
		if(senhaInput.getSenhaAtual().equals(senhaInput.getNovaSenha())) {
			throw new NegocioException("Nova senha deve ser diferente da atual");
		}
		
		usuario.setSenha(senhaInput.getNovaSenha());
		
		salvar(usuario);
	}

}
