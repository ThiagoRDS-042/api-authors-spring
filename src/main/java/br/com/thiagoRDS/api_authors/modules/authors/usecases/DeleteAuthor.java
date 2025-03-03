package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class DeleteAuthor {
  private final AuthorsRepository authorsRepository;

  public DeleteAuthor(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    this.authorsRepository.delete(author);
  }
}
