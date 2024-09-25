package com.developer.fillme.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${springdoc.api-docs.url-uat}")
    private String productUrl;

    @Value("${springdoc.api-docs.url-dev}")
    private String devUrl;

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(productUrl);
        prodServer.setDescription("Server URL in UAT environment");

        Contact contact = new Contact();
        contact.setEmail("https://developer.com");
        contact.setName("Fillme Developer");
        contact.setUrl("https://developer.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("FILLME API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.")
                .termsOfService("https://developer.com")
                .license(mitLicense);

        ExternalDocumentation documentation = new ExternalDocumentation();
        documentation.setDescription("DEV");
        documentation.setUrl("https://springshop.wiki.github.org/docs");

        // Created SecurityRequirement cho Bearer Authentication
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("Bearer Authentication");

        // Created SecurityScheme cho Bearer Authentication
        SecurityScheme scheme = new SecurityScheme();
        scheme.setBearerFormat("JWT");
        scheme.setScheme("bearer");
        scheme.setType(SecurityScheme.Type.HTTP);
        scheme.setDescription("Authentication for APP clients");

        Components components = new Components();
        components.addSecuritySchemes("Bearer Authentication", scheme);

        return new OpenAPI()
                .info(info)
                .externalDocs(documentation)
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(devServer, prodServer));
    }
}
