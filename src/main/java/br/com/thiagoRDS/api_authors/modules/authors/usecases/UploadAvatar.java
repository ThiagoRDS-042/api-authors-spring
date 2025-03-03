package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.thiagoRDS.api_authors.config.MinioConfig;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.FileNotReceivedException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.FileTooLargeException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidFileMimetypeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.MinioProvider;
import br.com.thiagoRDS.api_authors.utils.FileStorage;
import br.com.thiagoRDS.api_authors.utils.NormalizeFilename;

@Service
public class UploadAvatar {
  private final MinioConfig minioConfig;
  private final MinioProvider minioProvider;
  private final AuthorsRepository authorsRepository;

  public UploadAvatar(MinioConfig minioConfig, MinioProvider minioProvider, AuthorsRepository authorsRepository) {
    this.minioConfig = minioConfig;
    this.minioProvider = minioProvider;
    this.authorsRepository = authorsRepository;
  }

  public void execute(UploadAvatarDTO data) {
    MultipartFile file = data.file();

    if (file.isEmpty()) {
      throw new FileNotReceivedException();
    }

    if (file.getSize() > FileStorage.MAX_FILE_SIZE) {
      throw new FileTooLargeException();
    }

    if (!FileStorage.VALID_MIMETYPES.contains(file.getContentType())) {
      throw new InvalidFileMimetypeException();
    }

    Author author = this.authorsRepository.findById(data.authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    String originalFilename = file.getOriginalFilename();

    String filename = NormalizeFilename.normalize(originalFilename);

    if (author.getAvatar() != null) {
      this.minioProvider.deleteFile(this.minioConfig.getBucketName(), author.getAvatar());
    }

    this.minioProvider.uploadFile(this.minioConfig.getBucketName(), file, filename);

    author.setAvatar(filename);

    this.authorsRepository.save(author);
  }
}
