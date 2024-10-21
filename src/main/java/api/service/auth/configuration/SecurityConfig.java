package api.service.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v3/api-docs/**",         // Permet l'accès aux spécifications de l'API OpenAPI
                                "/swagger-ui/**",          // Permet l'accès aux ressources Swagger UI
                                "/swagger-ui.html",        // Permet l'accès à la page principale de Swagger UI
                                "/swagger-resources/**",   // Permet l'accès aux ressources de Swagger
                                "/webjars/**",             // Permet l'accès aux fichiers statiques utilisés par Swagger
                                "/api/auth/**"             // Permet l'accès aux endpoints d'authentification (ajoute cette ligne)
                        ).permitAll()                 // Autorise l'accès sans authentification
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .csrf(csrf -> csrf.disable());   // Désactive la protection CSRF

        return http.build();
    }
}
