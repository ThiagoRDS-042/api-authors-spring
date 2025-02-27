package br.com.authors.api_authors.modules.posts.dtos;

import jakarta.validation.constraints.NotBlank;

public record ListPostsDTO(
        String title,
        String keywords,
        String description,
        String content,
        @NotBlank Integer page,
        @NotBlank Integer pageSize) {
}
