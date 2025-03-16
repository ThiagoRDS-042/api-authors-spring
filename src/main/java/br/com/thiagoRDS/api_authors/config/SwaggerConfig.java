package br.com.thiagoRDS.api_authors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  private static final String SECURITY_SCHEME_NAME = "jwt_auth";

  @Bean
  OpenAPI openAPI() {
    Contact contact = new Contact().name("Thiago Rodrigues dos Santos")
        .email("thiagords042@gmail.com")
        .url("https://github.com/ThiagoRDS-042");

    Info info = new Info()
        .title("API Authors")
        .description("API to post a authors experiencies")
        .version("1.0")
        .summary("API to post a authors")
        .contact(contact);

    OpenAPI openAPI = new OpenAPI()
        .info(info)
        .schemaRequirement(SECURITY_SCHEME_NAME, this.createSecurityScheme());

    return openAPI;
  }

  private SecurityScheme createSecurityScheme() {
    SecurityScheme securityScheme = new SecurityScheme()
        .name(SECURITY_SCHEME_NAME)
        .scheme("bearer")
        .bearerFormat("JWT")
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER);

    return securityScheme;
  }
}
