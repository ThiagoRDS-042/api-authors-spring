package br.com.thiagoRDS.api_authors.modules.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;

public record MakeAuthor() {
        public static final Author AUTHOR = new Author(
                        UUID.randomUUID(),
                        "author",
                        "author@example.com",
                        "Test-123",
                        "Test123",
                        "avatar.png",
                        LocalDate.now().minusYears(19),
                        UUID.randomUUID(),
                        new Address(),
                        LocalDateTime.now(),
                        LocalDateTime.now());

        public static final RegisterAuthorDTO REGISTER_AUTHOR_DTO = new RegisterAuthorDTO(AUTHOR.getName(),
                        AUTHOR.getEmail(),
                        AUTHOR.getPassword(),
                        AUTHOR.getBirthdate().toString());

        public static final RegisterAuthorDTO AUTHOR_INVALID_AGE = new RegisterAuthorDTO(AUTHOR.getName(),
                        AUTHOR.getEmail(),
                        AUTHOR.getPassword(),
                        LocalDate.now().toString());
}
