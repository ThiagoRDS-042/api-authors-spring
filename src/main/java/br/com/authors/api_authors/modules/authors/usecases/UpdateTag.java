package br.com.authors.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class UpdateTag {
  private final AuthorsRepository authorsRepository;

  public UpdateTag(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId, String tag) {
    var author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    var tagAlreadyRegistered = this.authorsRepository.findByTag(tag);

    if (tagAlreadyRegistered.isPresent() && !tagAlreadyRegistered.get().getId().equals(author.getId())) {
      throw new AuthorAlreadyRegisteredException();
    }

    author.setTag(tag);

    this.authorsRepository.save(author);
  }
}
