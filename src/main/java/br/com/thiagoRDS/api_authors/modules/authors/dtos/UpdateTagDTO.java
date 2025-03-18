package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import br.com.thiagoRDS.api_authors.utils.SwaggerAuthorExample;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateTagDTO(
                @Schema(example = SwaggerAuthorExample.TAG, requiredMode = RequiredMode.REQUIRED) @NotBlank @Pattern(regexp = "[\\w-@$#*&!%()]+", message = "Invalid tag format") String tag) {
}