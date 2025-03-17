package br.com.thiagoRDS.api_authors.modules.posts.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.utils.SwaggerPostExample;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record PostResponseMapperDTO(
                @Schema(requiredMode = RequiredMode.REQUIRED) UUID id,
                @Schema(example = SwaggerPostExample.TITLE, requiredMode = RequiredMode.REQUIRED) String title,
                @Schema(example = SwaggerPostExample.CONTENT, requiredMode = RequiredMode.REQUIRED) String content,
                @Schema(example = SwaggerPostExample.DESCRIPTION, requiredMode = RequiredMode.REQUIRED) String description,
                @Schema(example = SwaggerPostExample.KEYWORDS, requiredMode = RequiredMode.REQUIRED) List<String> keywords,
                @Schema(requiredMode = RequiredMode.REQUIRED) AuthorReponseMapperDTO author,
                @Schema(example = SwaggerPostExample.UP, requiredMode = RequiredMode.REQUIRED) Integer up,
                @Schema(example = SwaggerPostExample.PUBLISHED_AT, requiredMode = RequiredMode.REQUIRED) LocalDateTime publishedAt) {
}
