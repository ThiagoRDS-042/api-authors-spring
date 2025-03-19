package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.thiagoRDS.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.FileTooLargeException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidFileMimetypeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.providers.StorageProvider.StorageProvider;
import br.com.thiagoRDS.api_authors.utils.FileStorage;
import br.com.thiagoRDS.api_authors.utils.NormalizeFilename;

@Service
public class UploadAvatar {
  private final StorageProvider storageProvider;
  private final AuthorsRepository authorsRepository;

  public UploadAvatar(StorageProvider storageProvider, AuthorsRepository authorsRepository) {
    this.storageProvider = storageProvider;
    this.authorsRepository = authorsRepository;
  }

  public void execute(UploadAvatarDTO data) {
    MultipartFile file = data.file();

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
      this.storageProvider.deleteFile(author.getAvatar());
    }

    this.storageProvider.uploadFile(file, filename);

    author.setAvatar(filename);

    this.authorsRepository.save(author);
  }
}
