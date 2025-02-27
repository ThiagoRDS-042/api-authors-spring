package br.com.authors.api_authors.modules.posts.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record PublishPostDTO(
        @NotBlank UUID authorId,
        @NotBlank UUID postId) {
}
