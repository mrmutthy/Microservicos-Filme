package br.edu.utfpr.api_recomendacao.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Recomendação",
        version = "1.0",
        description = "Documentação da API para obtenção de recomendações de filmes."
    )
)
public class OpenAPIConfig {
}
