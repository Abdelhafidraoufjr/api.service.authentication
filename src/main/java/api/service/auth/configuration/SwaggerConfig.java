package api.service.auth.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi apiAuthentication() {
        return GroupedOpenApi.builder()
                .group("api-authentication")
                .pathsToMatch("/api/auth/**")  // Chemin mis à jour pour correspondre à celui du contrôleur
                .build();
    }
}
