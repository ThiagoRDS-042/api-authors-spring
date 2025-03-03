package br.com.thiagoRDS.api_authors.modules.authors.mappers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;

public record AuthorMapper() {

  public static AuthorReponseMapperDTO ToHttp(Author author) {
    String avatarUrl = author.getAvatar() != null
        ? ServletUriComponentsBuilder.fromCurrentContextPath().path("/authors/avatar/")
            .path(author.getAvatar())
            .toUriString()
        : null;

    AuthorReponseMapperDTO authorMapper = new AuthorReponseMapperDTO(
        author.getId(),
        author.getTag(),
        author.getName(),
        author.getEmail(),
        author.getAddress(),
        avatarUrl,
        author.getBirthdate());

    return authorMapper;
  }
}
