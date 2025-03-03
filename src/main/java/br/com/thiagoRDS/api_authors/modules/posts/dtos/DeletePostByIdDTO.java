package br.com.thiagoRDS.api_authors.modules.posts.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record DeletePostByIdDTO(
                @NotBlank UUID authorId,
                @NotBlank UUID postId) {
}
