package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.utils.SwaggerAuthorExample;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AuthorReponseMapperDTO(
        @Schema(requiredMode = RequiredMode.REQUIRED) UUID id,
        @Schema(example = SwaggerAuthorExample.TAG, requiredMode = RequiredMode.REQUIRED) String tag,
        @Schema(example = SwaggerAuthorExample.NAME, requiredMode = RequiredMode.REQUIRED) String name,
        @Schema(example = SwaggerAuthorExample.EMAIL, requiredMode = RequiredMode.REQUIRED) String email,
        @Schema(requiredMode = RequiredMode.NOT_REQUIRED) AddressResponseMapperDTO address,
        @Schema(example = SwaggerAuthorExample.AVATAR_URL, requiredMode = RequiredMode.NOT_REQUIRED) String avatarUrl,
        @Schema(example = SwaggerAuthorExample.BIRTHDATE, requiredMode = RequiredMode.REQUIRED) LocalDate birthdate) {
}
