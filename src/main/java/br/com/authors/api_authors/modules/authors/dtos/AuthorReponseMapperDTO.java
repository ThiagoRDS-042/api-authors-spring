package br.com.authors.api_authors.modules.authors.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorReponseMapperDTO(UUID id, String name, String email, String tag, LocalDate birthdate) {
}
