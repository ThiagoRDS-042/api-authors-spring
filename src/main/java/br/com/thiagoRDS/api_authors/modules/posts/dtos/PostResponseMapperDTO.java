package br.com.thiagoRDS.api_authors.modules.posts.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;

public record PostResponseMapperDTO(
    UUID id,
    String title,
    String content,
    String description,
    String[] keywords,
    AuthorReponseMapperDTO author,
    Integer up,
    LocalDateTime publishedAt) {
}
