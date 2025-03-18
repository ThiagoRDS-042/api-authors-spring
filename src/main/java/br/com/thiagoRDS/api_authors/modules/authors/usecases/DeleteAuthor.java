package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.config.MinioConfig;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.MinioProvider;

@Service
public class DeleteAuthor {
  private final MinioConfig minioConfig;
  private final MinioProvider minioProvider;
  private final AuthorsRepository authorsRepository;

  public DeleteAuthor(MinioConfig minioConfig, MinioProvider minioProvider, AuthorsRepository authorsRepository) {
    this.minioConfig = minioConfig;
    this.minioProvider = minioProvider;
    this.authorsRepository = authorsRepository;
  }

  public void execute(UUID authorId) {
    Author author = this.authorsRepository.findById(authorId).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    if (author.getAvatar() != null) {
      this.minioProvider.deleteFile(this.minioConfig.getBucketName(), author.getAvatar());
    }

    this.authorsRepository.delete(author);
  }
}
