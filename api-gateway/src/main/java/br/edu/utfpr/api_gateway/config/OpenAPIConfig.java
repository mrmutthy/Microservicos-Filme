package br.edu.utfpr.api_gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Gateway",
        version = "1.0",
        description = "Documentação do Gateway."
    )
)
public class OpenAPIConfig {
}
