package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import br.com.thiagoRDS.api_authors.utils.SwaggerAuthorExample;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotMailPasswordControllerDTO(
    @Schema(example = SwaggerAuthorExample.EMAIL, requiredMode = RequiredMode.REQUIRED) @NotBlank @Email String email) {
}
