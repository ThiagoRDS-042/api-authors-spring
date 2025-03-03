package br.com.thiagoRDS.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;

public record AuthorWithPostsDTO(UUID id, String name, String email, String tag, LocalDate birthdate,
                Address address, List<PostWithouAuthorDTO> posts) {
}
