package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.StorageProvider.StorageProvider;

@Service
public class DeleteAuthor {
  private final StorageProvider storageProvider;
  private final AuthorsRepository authorsRepository;

  public DeleteAuthor(StorageProvider storageProvider, AuthorsRepository authorsRepository) {
    this.storageProvider = storageProvider;
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    if (author.getAvatar() != null) {
      this.storageProvider.deleteFile(author.getAvatar());
    }

    this.authorsRepository.delete(author);
  }
}
