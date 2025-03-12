package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import br.com.thiagoRDS.api_authors.config.MinioConfig;
import br.com.thiagoRDS.api_authors.modules.authors.dtos.UploadAvatarDTO;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.InvalidFileMimetypeException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.MinioProvider;

@ExtendWith(MockitoExtension.class)
public class UploadAvatarTest {
  @InjectMocks
  private UploadAvatar uploadAvatar;

  @Mock
  private MinioConfig minioConfig;

  @Mock
  private MinioProvider minioProvider;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to upload a new avatar")
  public void uploadAvatar() {
    Author author = MakeAuthor.AUTHOR.clone();

    byte[] inputArray = "avatar.png".getBytes();

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "avatar.png", "image/png", inputArray);

    UploadAvatarDTO uploadAvatar = new UploadAvatarDTO(author.getId(), mockMultipartFile);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));

    assertThatCode(() -> this.uploadAvatar.execute(uploadAvatar)).doesNotThrowAnyException();
    assertThat(author.getAvatar()).isInstanceOf(String.class);
  }

  @Test
  @DisplayName("Should not be able to upload a new avatar with file has a invalid mimetype")
  public void invalidFileMimetype() {
    Author author = MakeAuthor.AUTHOR.clone();

    byte[] inputArray = "avatar.png".getBytes();

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "avatar.png", "invalid-mimetype", inputArray);

    UploadAvatarDTO uploadAvatar = new UploadAvatarDTO(author.getId(), mockMultipartFile);

    assertThatThrownBy(() -> this.uploadAvatar.execute(uploadAvatar))
        .isInstanceOf(InvalidFileMimetypeException.class);
  }

  @Test
  @DisplayName("Should not be able to upload a new avatar with non-existing author")
  public void authorNotFound() {
    Author author = MakeAuthor.AUTHOR.clone();

    byte[] inputArray = "avatar.png".getBytes();

    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "avatar.png", "image/png", inputArray);

    UploadAvatarDTO uploadAvatar = new UploadAvatarDTO(author.getId(), mockMultipartFile);

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.uploadAvatar.execute(uploadAvatar))
        .isInstanceOf(AuthorNotFoundException.class);
  }
}
