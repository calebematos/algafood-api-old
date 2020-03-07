package com.calebematos.algafood.domain.repository;

import java.util.Optional;

import com.calebematos.algafood.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
}
