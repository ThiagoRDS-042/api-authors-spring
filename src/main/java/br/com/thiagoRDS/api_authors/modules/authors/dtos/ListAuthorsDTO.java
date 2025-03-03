package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import jakarta.validation.constraints.NotBlank;

public record ListAuthorsDTO(
        String email,
        String tag,
        @NotBlank Integer page,
        @NotBlank Integer pageSize) {
}
