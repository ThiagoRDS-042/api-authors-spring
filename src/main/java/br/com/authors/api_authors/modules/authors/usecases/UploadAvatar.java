package br.com.authors.api_authors.modules.authors.usecases;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.authors.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.exceptions.FileNotReceivedException;
import br.com.authors.api_authors.modules.authors.exceptions.FileTooLargeException;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidFileMimetypeException;
import br.com.authors.api_authors.modules.authors.exceptions.UploadFileErrorException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.utils.FileStorage;

@Service
public class UploadAvatar {
  private final Path storagePath;
  private final AuthorsRepository authorsRepository;

  public UploadAvatar(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
    this.storagePath = Paths.get(FileStorage.STORAGE_PATH).toAbsolutePath().normalize();
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

    String filename = originalFilename.split("\\.")[0] + "-" + System.currentTimeMillis() + "."
        + originalFilename.split("\\.")[1]; // TODO: ajust random filename and integrate with minio

    try {
      Path filePath = this.storagePath.resolve(filename);

      file.transferTo(filePath);

      author.setAvatar(filename);

      this.authorsRepository.save(author);
    } catch (IOException exception) {
      exception.printStackTrace();

      throw new UploadFileErrorException();
    }
  }
}
