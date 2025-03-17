package br.com.thiagoRDS.api_authors.modules.posts.dtos;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(
    @NotBlank String title,
    @NotBlank String content,
    @NotBlank String description,
    @NotNull @Size(min = 1, message = "keywords must be min 1 length") List<String> keywords,
    @NotBlank UUID authorId) {
}
