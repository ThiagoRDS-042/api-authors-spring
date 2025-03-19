package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.StorageProvider.StorageProvider;

@Service
public class GetAvatar {
  private final StorageProvider storageProvider;
  private final AuthorsRepository authorsRepository;

  public GetAvatar(StorageProvider storageProvider, AuthorsRepository authorsRepository) {
    this.storageProvider = storageProvider;
    this.authorsRepository = authorsRepository;
  }

  public InputStream execute(String avatar) {
    this.authorsRepository.findByAvatar(avatar).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    InputStream file = this.storageProvider.getFile(avatar);

    return file;
  }

}
