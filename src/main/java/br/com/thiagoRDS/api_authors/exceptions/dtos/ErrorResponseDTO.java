package br.com.thiagoRDS.api_authors.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record ErrorResponseDTO(
    @Schema(requiredMode = RequiredMode.REQUIRED) String message,
    @Schema(requiredMode = RequiredMode.REQUIRED) String code) {
}
