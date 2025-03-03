package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;

public record AuthorWithPostsReponseMapperDTO(
    UUID id,
    String tag,
    String name,
    String email,
    Address address,
    LocalDate birthdate,
    List<PostWithouAuthorDTO> posts) {
}
