package br.com.authors.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class GetAuthorById {
  private final AuthorsRepository authorsRepository;

  public GetAuthorById(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public Author execute(UUID authorId) {
    var author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    return author;
  }
}
