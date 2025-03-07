package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.config.MinioConfig;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;
import br.com.thiagoRDS.api_authors.providers.MinioProvider;

@ExtendWith(MockitoExtension.class)
public class GetAvatarTest {
  @InjectMocks
  private GetAvatar getAvatar;

  @Mock
  private MinioConfig minioConfig;

  @Mock
  private MinioProvider minioProvider;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to get a author avatar")
  public void getAvatar() {
    String avatar = "avatar.png";

    InputStream nullFile = InputStream.nullInputStream();

    when(this.authorsRepository.findByAvatar(avatar)).thenReturn(Optional.of(MakeAuthor.AUTHOR));
    when(this.minioProvider.getFile(this.minioConfig.getBucketName(), avatar))
        .thenReturn(nullFile);

    InputStream file = this.getAvatar.execute(avatar);

    assertThat(file).isEqualTo(nullFile);
  }

  @Test
  @DisplayName("Should not be able to get a author avatar with non-existing author")
  public void authorNotFound() {
    String avatar = "non-existing-avatar.png";

    when(this.authorsRepository.findByAvatar(avatar)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.getAvatar.execute(avatar))
        .isInstanceOf(AuthorNotFoundException.class);
  }
}
