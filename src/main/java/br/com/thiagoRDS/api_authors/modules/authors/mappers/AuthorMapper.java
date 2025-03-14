package br.com.thiagoRDS.api_authors.modules.authors.mappers;

import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;

public record AuthorMapper() {

  public static AuthorReponseMapperDTO ToHttp(Author author) {
    String avatarUrl = null;

    if (author.getAvatar() != null) {
      UriComponentsBuilder currentUri = new CurrentContextProvider().getUri();

      avatarUrl = currentUri.path("/authors/avatar/").path(author.getAvatar()).toUriString();
    }

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
