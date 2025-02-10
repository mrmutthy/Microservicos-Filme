package br.edu.utfpr.api_filme.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Filmes",
        version = "1.0",
        description = "Documentação da API para gerenciamento de filmes."
    )
)
public class OpenAPIConfig {
}
