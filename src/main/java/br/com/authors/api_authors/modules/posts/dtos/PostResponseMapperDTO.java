package br.com.authors.api_authors.modules.posts.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.authors.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;

public record PostResponseMapperDTO(
        UUID id,
        String title,
        String content,
        String description,
        String[] keywords,
        AuthorReponseMapperDTO author,
        Integer like,
        Integer desLike,
        LocalDateTime publishedAt) {
}
