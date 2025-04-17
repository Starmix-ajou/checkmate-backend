package com.starmix.checkmate.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Checkmate API")
                .version("1.0.0")
                .description("Checkmate API Docs");

        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        List<Server> servers = List.of(
                new Server().url("https://api.checkmate.it.kr").description("Prod Server"),
                new Server().url("http://localhost:8080").description("Local Server")
        );

        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        Components components = new Components();
        components.addSecuritySchemes("JWT", bearerAuth);


        return new OpenAPI()
                .servers(servers)
                .components(components)
                .addSecurityItem(addSecurityItem)
                .info(info);
    }
}

