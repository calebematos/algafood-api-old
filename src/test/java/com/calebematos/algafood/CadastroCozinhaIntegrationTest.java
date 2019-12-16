package com.calebematos.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calebematos.algafood.domain.exception.EntidadeEmUsoException;
import com.calebematos.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.calebematos.algafood.domain.model.Cozinha;
import com.calebematos.algafood.domain.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void deveCadastrarCozinhaComSucesso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Japonesa");

		cozinha = cozinhaService.salvar(cozinha);

		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalhar_QuandoSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		cozinha = cozinhaService.salvar(cozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		cozinhaService.excluir(1L);
	}

	@Test(expected = EntidadeNaoEncontradaException.class)
	public void deveFalhar_QuandoExcluirCozinhaInxistence() {
		cozinhaService.excluir(9999L);
	}
}
