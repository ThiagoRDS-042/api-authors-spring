package br.com.thiagoRDS.api_authors.modules.authors.mappers;

import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.AddressResponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.AuthorReponseMapperDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider.Impl.CurrentContextProviderImpl;

public record AuthorMapper() {

  public static AuthorReponseMapperDTO ToHttp(Author author) {
    String avatarUrl = null;

    if (author.getAvatar() != null) {
      UriComponentsBuilder currentUri = new CurrentContextProviderImpl().getUri();

      avatarUrl = currentUri.path("/authors/avatar/").path(author.getAvatar()).toUriString();
    }

    AddressResponseMapperDTO address = author.getAddress() != null ? AddressMapper.ToHttp(author.getAddress()) : null;

    AuthorReponseMapperDTO authorMapper = new AuthorReponseMapperDTO(
        author.getId(),
        author.getTag(),
        author.getName(),
        author.getEmail(),
        address,
        avatarUrl,
        author.getBirthdate());

    return authorMapper;
  }
}
