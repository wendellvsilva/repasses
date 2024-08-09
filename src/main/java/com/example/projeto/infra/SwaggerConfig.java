package com.example.projeto.infra;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Repasses ",
                description = "API Repasses",
                version = "V1",
                contact = @Contact(
                        name = "Wendell Vinicius",
                        url = "https://github.com/wendellvsilva",
                        email = "wendell.vinicius@ufrpe.br"
                )
        ),
        servers = @Server(url = "/")
)
public class SwaggerConfig {
}