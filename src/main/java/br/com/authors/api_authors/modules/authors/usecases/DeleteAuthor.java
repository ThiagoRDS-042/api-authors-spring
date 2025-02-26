package br.com.authors.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class DeleteAuthor {
  private final AuthorsRepository authorsRepository;

  public DeleteAuthor(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId) {
    var author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    this.authorsRepository.delete(author);
  }
}
