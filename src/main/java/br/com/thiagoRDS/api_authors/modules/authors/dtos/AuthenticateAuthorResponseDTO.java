package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AuthenticateAuthorResponseDTO(
                @Schema(requiredMode = RequiredMode.REQUIRED) String token,
                @Schema(requiredMode = RequiredMode.REQUIRED) Long expiresIn) {
}
