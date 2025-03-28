package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidAgeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.utils.GenerateTag;
import br.com.thiagoRDS.api_authors.utils.ValidAge;

@Service
public class RegisterAuthor {
  private final PasswordEncoder passwordEncoder;
  private final AuthorsRepository authorsRepository;

  RegisterAuthor(AuthorsRepository authorsRepository, PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.authorsRepository = authorsRepository;
  }

  public Author execute(RegisterAuthorDTO data) {
    this.authorsRepository.findByEmail(data.email())
        .ifPresent((author) -> {
          throw new AuthorAlreadyRegisteredException();
        });

    LocalDate birthdate = LocalDate.parse(data.birthdate());

    Long months = ChronoUnit.MONTHS.between(birthdate, LocalDate.now());

    if (months < ValidAge.VALUE) {
      throw new InvalidAgeException();
    }

    String tag = GenerateTag.generate(data.name());

    Optional<Author> authorExists = this.authorsRepository.findByTag(tag);

    while (authorExists.isPresent()) {
      tag = GenerateTag.generate(data.name());

      authorExists = this.authorsRepository.findByTag(tag);
    }

    String passwordEncoded = this.passwordEncoder.encode(data.password());

    Author author = new Author(data.name(), data.email(), tag, passwordEncoded, birthdate);

    author = this.authorsRepository.save(author);

    return author;
  }
}
