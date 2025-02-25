package br.com.authors.api_authors.modules.authors.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class ListAuthors {
  private AuthorsRepository authorsRepository;

  public ListAuthors(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public List<Author> execute() {
    var authors = this.authorsRepository.findAll();

    return authors;
  }
}
