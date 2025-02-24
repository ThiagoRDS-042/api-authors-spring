package br.com.authors.api_authors.modules.authors.usecases;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.authors.api_authors.modules.authors.dtos.RegisterAuthorDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class RegisterAuthor {
  @Autowired
  private AuthorsRepository authorsRepository;

  public Author execute(RegisterAuthorDTO data) {
    this.authorsRepository.findByTagOrEmail(data.tag(), data.email())
        .ifPresent((author) -> {
          throw new AuthorAlreadyRegisteredException();
        });

    var today = LocalDateTime.now();

    var author = new Author(data.name(), data.email(), data.tag(), data.birthdate(), today);

    author = this.authorsRepository.save(author);

    return author;
  }
}
