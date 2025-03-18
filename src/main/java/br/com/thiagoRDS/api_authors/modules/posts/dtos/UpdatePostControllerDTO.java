package br.com.thiagoRDS.api_authors.modules.posts.dtos;

import java.util.List;

import br.com.thiagoRDS.api_authors.utils.SwaggerPostExample;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePostControllerDTO(
    @Schema(example = SwaggerPostExample.TITLE, requiredMode = RequiredMode.REQUIRED) @NotBlank String title,
    @Schema(example = SwaggerPostExample.CONTENT, requiredMode = RequiredMode.REQUIRED) @NotBlank String content,
    @Schema(example = SwaggerPostExample.DESCRIPTION, requiredMode = RequiredMode.REQUIRED) @NotBlank String description,
    @ArraySchema(schema = @Schema(example = SwaggerPostExample.KEYWORDS, requiredMode = RequiredMode.REQUIRED)) @NotNull @Size(min = 1, message = "keywords must be min 1 length") List<String> keywords) {
}
