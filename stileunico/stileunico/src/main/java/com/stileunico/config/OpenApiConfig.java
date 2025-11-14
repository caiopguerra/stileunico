package com.stileunico.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI stileUnicoOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Stile Unico API")
                                                .description("API para gerenciamento da loja de roupas")
                                                .version("v1.0"))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth",
                                                                new SecurityScheme()
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
        }

        @Bean
        public OpenApiCustomizer hideDataRest() {
                return openApi -> openApi.getPaths().entrySet()
                                .removeIf(entry -> entry.getKey().startsWith("/api") == false && // mantém SOMENTE
                                                                                                 // endpoints da sua API
                                                entry.getKey().contains("search") // remove o /search do Spring Data
                                                                                  // REST
                                );
        }
}
