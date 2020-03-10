package com.calebematos.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calebematos.algafood.domain.exception.NegocioException;
import com.calebematos.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.calebematos.algafood.domain.model.Grupo;
import com.calebematos.algafood.domain.model.Usuario;
import com.calebematos.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;

	public Usuario buscar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {

		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}

		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscar(usuarioId);

		if (!usuario.getSenha().equals(senhaAtual)) {
			throw new NegocioException("Senha atual informada não conincide com a senha do usuário");
		}

		if (senhaAtual.equals(novaSenha)) {
			throw new NegocioException("Nova senha deve ser diferente da atual");
		}

		usuario.setSenha(novaSenha);

		salvar(usuario);

	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = grupoService.buscar(grupoId);
		
		usuario.removerGrupo(grupo);
	}

}
