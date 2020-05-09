package com.calebematos.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.calebematos.algafood.domain.service.EnvioEmailService;
import com.calebematos.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.calebematos.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.calebematos.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EnvioEmailService emailService() {

		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			case SANDBOX:
				return new SandboxEnvioEmailService();
			default:
				return null;
		}
	}
}
