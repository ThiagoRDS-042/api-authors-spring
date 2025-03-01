package br.com.authors.api_authors.modules.authors.usecases;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import br.com.authors.api_authors.config.MinioConfig;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.providers.MinioProvider;

@Service
public class GetAvatar {
  private final MinioConfig minioConfig;
  private final MinioProvider minioProvider;
  private final AuthorsRepository authorsRepository;

  public GetAvatar(MinioConfig minioConfig, MinioProvider minioProvider, AuthorsRepository authorsRepository) {
    this.minioConfig = minioConfig;
    this.minioProvider = minioProvider;
    this.authorsRepository = authorsRepository;
  }

  public InputStream execute(String avatar) {
    this.authorsRepository.findByAvatar(avatar).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    InputStream file = this.minioProvider.getFile(this.minioConfig.getBucketName(), avatar);

    return file;
  }

}
