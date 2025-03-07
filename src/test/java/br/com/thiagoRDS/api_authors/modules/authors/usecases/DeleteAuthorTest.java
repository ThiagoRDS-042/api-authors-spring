package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.utils.MakeAuthor;

@ExtendWith(MockitoExtension.class)
public class DeleteAuthorTest {
  @InjectMocks
  private DeleteAuthor deleteAuthor;

  @Mock
  private AuthorsRepository authorsRepository;

  @Test
  @DisplayName("Should be able to delete a author")
  public void deleteAuthor() {
    Author author = MakeAuthor.AUTHOR;

    when(this.authorsRepository.findById(author.getId())).thenReturn(Optional.of(author));

    assertThatCode(() -> this.deleteAuthor.execute(author.getId())).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Should not be able to delete a non-existing author")
  public void authorNotFound() {
    UUID authorId = UUID.randomUUID();

    when(this.authorsRepository.findById(authorId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> this.deleteAuthor.execute(authorId))
        .isInstanceOf(AuthorNotFoundException.class);
  }
}
