package br.com.authors.api_authors.modules.authors.dtos;

import java.time.LocalDateTime;

public record RegisterAuthorDTO(String name, String email, String tag, LocalDateTime birthdate) {
}
