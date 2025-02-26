package br.com.authors.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.util.UUID;

import br.com.authors.api_authors.modules.authors.entities.Address;

public record AuthorReponseMapperDTO(
        UUID id,
        String tag,
        String name,
        String email,
        Address address,
        LocalDate birthdate) {
}
