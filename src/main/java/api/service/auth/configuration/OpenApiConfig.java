package api.service.auth.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api.Service.Auth")
                        .version("1.0.1")
                        .description("Documentation de l'API pour la gestion des utilisateurs, des rôles, des permissions, des sessions et des tentatives de connexion."));
    }
}
