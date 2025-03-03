package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class UpdateTag {
  private final AuthorsRepository authorsRepository;

  public UpdateTag(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId, String tag) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    Optional<Author> tagAlreadyRegistered = this.authorsRepository.findByTag(tag);

    if (tagAlreadyRegistered.isPresent() && !tagAlreadyRegistered.get().getId().equals(author.getId())) {
      throw new AuthorAlreadyRegisteredException();
    }

    author.setTag(tag);
    author.setUpdtaedAt(LocalDateTime.now());

    this.authorsRepository.save(author);
  }
}
