package br.csi.sistema_biblioteca;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "API Sistema de Biblioteca",
				version = "1.0",
				description = "Documentação da API do sistema de gerenciamento de biblioteca",
				contact = @Contact(name = "Suporte", email = "davi.hirata@gmail.com")
		)
)

@SpringBootApplication
public class SistemaBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaBibliotecaApplication.class, args);
	}

}
