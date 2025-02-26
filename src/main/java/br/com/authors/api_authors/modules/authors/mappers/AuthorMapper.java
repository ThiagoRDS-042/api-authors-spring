package br.com.authors.api_authors.modules.authors.mappers;

import br.com.authors.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;

public record AuthorMapper() {
  public static AuthorReponseMapperDTO ToHttp(Author author) {
    var authorMapper = new AuthorReponseMapperDTO(
        author.getId(),
        author.getTag(),
        author.getName(),
        author.getEmail(),
        author.getAddress(),
        author.getBirthdate());

    return authorMapper;
  }
}
