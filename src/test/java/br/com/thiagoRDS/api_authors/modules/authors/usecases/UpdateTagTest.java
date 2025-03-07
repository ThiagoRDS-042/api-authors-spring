package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Address;
import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorAlreadyRegisteredException;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@ExtendWith(MockitoExtension.class)
public class UpdateTagTest {
  @InjectMocks
  private UpdateTag updateTag;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to update author tag")
  public void updateTag() {
    Author author = MakeAuthor.AUTHOR;

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
    when(this.authorsRepository.findByTag(author.getTag())).thenReturn(Optional.empty());

    assertThatCode(() -> this.updateTag.execute(author.getId(), author.getTag())).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to update author tag with non-exsiting author")
  public void authorNotFound() {
    UUID authorId = UUID.randomUUID();

    when(this.authorsRepository.findById(authorId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.updateTag.execute(authorId, "author-tag"))
        .isInstanceOf(AuthorNotFoundException.class);
  }

  @Test
  @DisplayName("Should not be able to update author tag with same tag anoter author")
  public void tagAlreadyExists() {
    Author author = MakeAuthor.AUTHOR;
    Author anotherAuthor = new Author(
        UUID.randomUUID(),
        "author",
        "author@example.com",
        "Test-123",
        "Test123",
        "avatar.png",
        LocalDate.now().minusYears(19),
        UUID.randomUUID(),
        new Address(),
        LocalDateTime.now(),
        LocalDateTime.now());

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));
    when(this.authorsRepository.findByTag(author.getTag())).thenReturn(Optional.of(anotherAuthor));

    assertThatThrownBy(() -> this.updateTag.execute(author.getId(),
        author.getTag()))
        .isInstanceOf(AuthorAlreadyRegisteredException.class);
  }
}
